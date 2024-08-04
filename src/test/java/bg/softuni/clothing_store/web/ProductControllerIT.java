package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.service.CloudinaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CloudinaryService mockCloudinaryService;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "testUsername", roles = {"ADMIN"})
    void testViewAddProduct() throws Exception {
        mockMvc.perform(get("/administration/add-product"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("subCategoryData"))
                .andExpect(model().attributeExists("sizeData"))
                .andExpect(model().attributeExists("colorData"));
    }

    @Test
    @WithMockUser(username = "testUsername", roles = {"ADMIN"})
    void testDoAddProduct_Success() throws Exception {

        MockMultipartFile firstImage = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        Map<String, MultipartFile> toUpload = Map.of("firstImage", firstImage);
        when(mockCloudinaryService.uploadFile(firstImage)).thenReturn("uploadSuccessful");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/add-product")
                        .file("firstImage", firstImage.getBytes())
                        .file("secondImage", null)
                        .file("thirdImage", null)
                        .file("fourthImage", null)
                        .file("fifthImage", null)
                        .param("name", "testProduct")
                        .param("description", "testDescription")
                        .param("price", "125.55")
                        .param("quantity", "5")
                        .param("color", "BLUE")
                        .param("size", "M")
                        .param("category", "MEN")
                        .param("subCategory", "JACKET")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administration"));

        Optional<Product> byId = productRepository.findByName("testProduct");
        Assertions.assertTrue(byId.isPresent());
        Product product = byId.get();
        Assertions.assertEquals("testDescription", product.getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(125.55), product.getPrice());
        Assertions.assertEquals(5, product.getQuantity());
        Assertions.assertEquals(product.getColor().stream().findAny().get().getColorName().toString(), "BLUE");
        Assertions.assertEquals(product.getSize().stream().findFirst().get().getSizeName().toString(), "M");
        Assertions.assertEquals("MEN", product.getCategory().getCategory().toString());
        Assertions.assertEquals("JACKET", product.getSubCategory().getSubCategory().toString());

    }

}
