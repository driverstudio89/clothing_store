package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.*;
import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.OrderInfoDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService mockUserService;

    @Autowired
    private RestClient mockOrderRestClient;

    @MockBean
    private OrderService mockOrderService;

    @Mock
    private User mockUser = new User();

    private final Status status = new Status(StatusType.NEW);


    private final OrderInfoDto orderInfoDto = new OrderInfoDto()
            .setId(11L)
            .setOrderItems(new ArrayList<>())
            .setTotal(BigDecimal.TEN)
            .setStatus(status)
            .setPaymentType(PaymentType.CARD)
            .setDeliveryType(DeliveryType.ECONT)
            .setCreated(LocalDateTime.now())
            .setModified(LocalDateTime.now())
            .setUser(mockUser)
            .setFirstName("testFirstName")
            .setLastName("testLastName")
            .setEmail("testEmail")
            .setAddress("testAddress")
            .setPhoneNumber("testPhoneNumber")
            .setCountry("testCountry")
            .setCity("testCity")
            .setZip("testZip")
            ;

    Size size = new Size().setSizeName(SizeName.M);
    Color color = new Color().setColorName(ColorName.BLUE);
    Category category = new Category().setCategory(CategoryType.MEN);
    SubCategory subCategory = new SubCategory().setSubCategory(SubCategoryType.JACKET);

    private final Product testProduct = new Product()
            .setId(11L)
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
            .setVoted(8)
            .setInStock(true);

    CartItem testCartItem = new CartItem()
            .setId(11L)
            .setProduct(testProduct)
            .setSizes(size)
            .setColors(color)
            .setQuantity(2)
            .setUser(mockUser);

    private final User testUser = new User()
            .setId(11L)
            .setUsername("testUser")
            .setEmail("test@test.com")
            .setFirstName("Petar")
            .setLastName("Petrov")
            .setPassword("secretpass")
            .setRoles(List.of(
                    new Role().setName(UserRole.ADMIN),
                    new Role().setName(UserRole.USER)
            ))
            .setCartItems(Set.of(testCartItem));

    @Test
    @Disabled
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testDoCheckout() throws Exception {

        when(mockUserService.getUser()).thenReturn(testUser);
        when(mockUserService.getCartTotal()).thenReturn(testProduct.getPrice());
        doNothing().when(mockOrderRestClient.post());


        mockMvc.perform(post("/users/cart/checkout")
                        .param("id", "11")
                        .param("firstName", "testFirstName")
                        .param("lastName", "testLastName")
                        .param("email", "test@Email")
                        .param("phoneNumber", "123456789")
                        .param("address", "testAddress")
                        .param("country", "testCountry")
                        .param("city", "testCity")
                        .param("zip", "testZip")
                        .param("deliveryOptions", "SPEEDY")
                        .param("paymentOptions", "ON_DELIVERY")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/orders"));
    }

    @Test
    @Disabled
    @WithMockUser(username = "testUsername", roles = {"ADMIN"})
    void testGetOrderDetails() throws Exception {
        long productId = 11L;

        when(mockOrderService.getOrderDetails(productId)).thenReturn(orderInfoDto);

        mockMvc.perform(get("/administration/orders/order-" + productId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("orderDetails"))
                .andExpect(model().attributeExists("orderTotal"))
                .andExpect(model().attributeExists("newOrder"))
                .andExpect(model().attributeExists("processingOrder"));
    }

}
