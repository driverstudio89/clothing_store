package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.data.*;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.service.CloudinaryService;
import bg.softuni.clothing_store.service.exception.ObjectNotFoundException;
import bg.softuni.clothing_store.service.session.UserHelperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CloudinaryService cloudinaryService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ColorRepository colorRepository;

    @MockBean
    private UserHelperService mockUserHelperService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;


    @BeforeEach
    public void setUp() {
        cartItemRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();

        User user = userRepository.save(new User()
                .setUsername("testUsername")
                .setEmail("pesho@mail.com")
                .setFirstName("Petar")
                .setLastName("Petrov")
                .setPassword("secretpass"))
                .setCartItems(new HashSet<>());
        userRepository.save(user);

        Size size = sizeRepository.findBySizeName(SizeName.M);
        Color color = colorRepository.findByColorName(ColorName.BLUE);
        Category category = categoryRepository.findByCategory(CategoryType.MEN);
        SubCategory subCategory = subCategoryRepository.findBySubCategory(SubCategoryType.JACKET);

        Product testProduct = new Product()
                .setName("testProduct")
                .setPrice(BigDecimal.valueOf(15.55))
                .setDescription("testDescription")
                .setSize(Set.of(size))
                .setColor(Set.of(color))
                .setQuantity(2)
                .setImages(List.of("firstImage", "secondImage"))
                .setCreated(LocalDate.now())
                .setModified(LocalDate.now())
                .setCategory(category)
                .setSubCategory(subCategory)
                .setRating("4.44")
                .setStars(40)
                .setVoted(8);
        productRepository.save(testProduct);
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

        MockMultipartFile firstImage = new MockMultipartFile("firstImage.jpg", "firstImage".getBytes());
        Map<String, MultipartFile> toUpload = Map.of("firstImage", firstImage);
        when(cloudinaryService.uploadFile(toUpload.get("firstImage"))).thenReturn("uploadSuccessful");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/add-product")
                        .file("firstImage", firstImage.getBytes())
                        .file("secondImage", firstImage.getBytes())
                        .file("thirdImage", firstImage.getBytes())
                        .file("fourthImage", firstImage.getBytes())
                        .file("fifthImage", firstImage.getBytes())
                        .param("name", "addProductTest")
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

        Optional<Product> byId = productRepository.findByName("addProductTest");
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

    @Test
    @WithMockUser(username = "testUsername", roles = {"ADMIN"})
    void testDoAddProduct_HasErrors() throws Exception {

        MockMultipartFile firstImage = new MockMultipartFile("firstImage.jpg", "firstImage".getBytes());
        Map<String, MultipartFile> toUpload = Map.of("firstImage", firstImage);
        when(cloudinaryService.uploadFile(toUpload.get("firstImage"))).thenReturn("uploadSuccessful");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/add-product")
                        .file("firstImage", firstImage.getBytes())
                        .file("secondImage", firstImage.getBytes())
                        .file("thirdImage", firstImage.getBytes())
                        .file("fourthImage", firstImage.getBytes())
                        .file("fifthImage", firstImage.getBytes())
                        .param("name", "t")
                        .param("description", "t")
                        .param("price", "")
                        .param("quantity", "")
                        .param("color", "")
                        .param("size", "")
                        .param("category", "")
                        .param("subCategory", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administration/add-product"));

        Optional<Product> byId = productRepository.findByName("t");
        Assertions.assertTrue(byId.isEmpty());
    }

    @Test
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testProductDetails() throws Exception {
        Long id = productRepository.findByName("testProduct").get().getId();
        mockMvc.perform(get("/products/details/" + id))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("productDetails"))
                .andExpect(model().attributeExists("reviewInfoDto"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testProductAddToCart_Success() throws Exception {
        Product product = productRepository.findByName("testProduct").get();
        Long id = product.getId();

        User user = userRepository.findByUsername("testUsername").get();

        when(mockUserHelperService.getUser()).thenReturn(user);

        mockMvc.perform(post("/products/add-to-cart/" + id)
                        .param("quantity", "2")
                        .param("size", "M")
                        .param("color", "BLUE")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        CartItem cartItem = userRepository.findByUsername("testUsername").get().getCartItems().stream().findFirst().get();
        Assertions.assertEquals(product.getId(), cartItem.getProduct().getId());
        Assertions.assertEquals(product.getName(), cartItem.getProduct().getName());
        Assertions.assertEquals(product.getPrice(), cartItem.getProduct().getPrice());

        Assertions.assertEquals(product.getColor().stream().findFirst().get().getColorName(), cartItem.getProduct().getColor().stream().findFirst().get().getColorName());
        Assertions.assertEquals(product.getSize().stream().findFirst().get().getSizeName(), cartItem.getProduct().getSize().stream().findFirst().get().getSizeName());

        Assertions.assertEquals(product.getDescription(), cartItem.getProduct().getDescription());

        Assertions.assertEquals(product.getCategory().getCategory(), cartItem.getProduct().getCategory().getCategory());
        Assertions.assertEquals(product.getSubCategory().getSubCategory(), cartItem.getProduct().getSubCategory().getSubCategory());

        Assertions.assertEquals(product.getRating(), cartItem.getProduct().getRating());
        Assertions.assertEquals(product.getStars(), cartItem.getProduct().getStars());
        Assertions.assertEquals(product.getVoted(), cartItem.getProduct().getVoted());
        Assertions.assertEquals(product.getCreated(), cartItem.getProduct().getCreated());

        Assertions.assertEquals(product.getQuantity(), cartItem.getProduct().getQuantity());
    }

    @Test
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testProductAddToCart_ValidationErrors() throws Exception {
        Product product = productRepository.findByName("testProduct").get();
        Long id = product.getId();

        User user = userRepository.findByUsername("testUsername").get();

        when(mockUserHelperService.getUser()).thenReturn(user);

        mockMvc.perform(post("/products/add-to-cart/" + id)
                        .param("quantity", "-1")
                        .param("size", "")
                        .param("color", "")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/details/" + id));

        Optional<CartItem> optionalCartItem = userRepository.findByUsername("testUsername").get().getCartItems().stream().findAny();
        Assertions.assertTrue(optionalCartItem.isEmpty());

    }

    @Test()
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testProductAddToCart_AddingFailed() throws Exception {
        Product product = productRepository.findByName("testProduct").get();
        long id = product.getId();

        mockMvc.perform(post("/products/add-to-cart/" + id)
                        .param("quantity", "2")
                        .param("size", "M")
                        .param("color", "BLUE")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/details/" + id));

        Optional<CartItem> optionalCartItem = userRepository.findByUsername("testUsername").get().getCartItems().stream().findAny();
        Assertions.assertTrue(optionalCartItem.isEmpty());
    }

}
