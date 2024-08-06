package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.*;
import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestClient;

import java.util.Optional;
import java.util.Set;

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
    @Mock
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

    @Mock
    Product mockProduct = new Product()
            .setId(11L);

    @Mock
    CartItem mockCartItem = new CartItem()
            .setProduct(mockProduct);

    @Mock
    User mockUser = new User()
            .setCartItems(Set.of(mockCartItem));

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
                mockUserHelperService,
                mockUserRepository
        );
    }

    @Test
    void testCreateOrder() {
        ClientInfoDto clientInfoDto = createClientInfoDto();

        when(mockProductRepository.findById(mockProduct.getId())).thenReturn(Optional.ofNullable(mockProduct));
        when(mockUserService.getUser()).thenReturn(mockUser);
        when(mockUserService.getUser().getCartItems()).thenReturn(Set.of(mockCartItem));

        boolean isCreated = toTest.createOrder(clientInfoDto); //todo is not working


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
