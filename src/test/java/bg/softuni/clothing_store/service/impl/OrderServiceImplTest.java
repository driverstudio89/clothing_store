package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.*;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.*;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private OrderServiceImpl toTest;

    private final ModelMapper modelMapper = new ModelMapper();


    @Mock
    ProductRepository mockProductRepository;
    @Mock
    SizeRepository mockSizeRepository;
    @Mock
    ColorRepository mockColorRepository;
    @MockBean
    RestClient mockOrdersRestClient;
    @Mock
    UserService mockUserService;
    @Mock
    StatusRepository mockStatusRepository;
    @Mock
    CartItemService mockCartItemService;
    @Mock
    UserHelperService mockUserHelperService;
    @Mock
    UserRepository mockUserRepository;

    Size size = new Size().setSizeName(SizeName.M);
    Color color = new Color().setColorName(ColorName.BLUE);
    Category category = new Category().setCategory(CategoryType.MEN);
    SubCategory subCategory = new SubCategory().setSubCategory(SubCategoryType.JACKET);


    User mockUser = new User().setId(11L);

    @Mock
    Product mockProduct = new Product()
            .setId(11L);

    @Mock
    CartItem mockCartItem = new CartItem()
            .setProduct(mockProduct);

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


    @BeforeEach
    void setUp() {
        toTest = new OrderServiceImpl(
                mockProductRepository,
                mockSizeRepository,
                mockColorRepository,
                mockOrdersRestClient,
                mockUserService,
                modelMapper,
                mockStatusRepository,
                mockCartItemService,
                mockUserRepository
        );
    }

    @Test
    @Disabled
    void testCreateOrder() {
        ClientInfoDto clientInfoDto = createClientInfoDto();
//        when(mockUserService.getUser()).thenReturn(testUser);
//        doNothing().when(mockOrdersRestClient.post());
//
//
//        toTest.createOrder(clientInfoDto);
    }



    private static ClientInfoDto createClientInfoDto() {
        return new ClientInfoDto()
                .setId(11L)
                .setFirstName("testFirstName")
                .setLastName("testLastName")
                .setEmail("testEmail")
                .setPhoneNumber("testPhoneNumber")
                .setAddress("testAddress")
                .setCountry("testCountry")
                .setCity("testCity")
                .setZip("testZip")
                .setDeliveryOptions(DeliveryType.ECONT.toString())
                .setPaymentOptions(PaymentType.ON_DELIVERY.toString());
    }


}
