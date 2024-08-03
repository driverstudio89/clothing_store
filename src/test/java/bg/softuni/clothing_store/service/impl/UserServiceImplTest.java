package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.RoleRepository;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.model.enums.UserRole;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.CartItemInfoDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final String TEST_USERNAME = "test_username";
    private static final String TEST_EMAIL = "petar@mail.com";


    private UserServiceImpl toTest;

    private final ModelMapper modelMapper = new ModelMapper();

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private UserHelperService mockUserHelperService;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private ProductRepository mockProductRepository;

    @Mock UserServiceImpl mockUserService;

    @Mock
    private Product mockProduct = new Product();

    @Mock
    private User mockUser;

    private final CartItem cartItem = new CartItem()
            .setId(11L)
            .setProduct(mockProduct)
            .setQuantity(5)
            .setSizes(new Size().setSizeName(SizeName.M))
            .setColors(new Color().setColorName(ColorName.BLUE))
            .setUser(mockUser);

    private final User testUser = new User()
            .setId(11L)
            .setUsername(TEST_USERNAME)
            .setEmail(TEST_EMAIL)
            .setFirstName("Petar")
            .setLastName("Petrov")
            .setPassword("secretpass")
            .setRoles(List.of(
                    new Role().setName(UserRole.ADMIN),
                    new Role().setName(UserRole.USER)
            ))
            .setCartItems(
                    Set.of(cartItem)
            );

    private UserRegisterDto createUserRegisterDto() {
        UserRegisterDto userRegisterDto = new UserRegisterDto()
                .setUsername(TEST_USERNAME)
                .setEmail(TEST_EMAIL)
                .setFirstName("Petar")
                .setLastName("Petrov")
                .setPassword("secretpass")
                .setConfirmPassword("secretpassword");

        when(mockPasswordEncoder.encode(userRegisterDto.getPassword()))
                .thenReturn(userRegisterDto.getPassword() + userRegisterDto.getPassword());
        return userRegisterDto;
    }

    @BeforeEach
    void setUp() {
        toTest = new UserServiceImpl(
                mockUserRepository,
                mockPasswordEncoder,
                new ModelMapper(),
                mockUserHelperService,
                mockRoleRepository,
                mockProductRepository);

    }

    @Test
    void testUserRegistration_Success() {
        //Arrange
        UserRegisterDto userRegisterDto = createUserRegisterDto();

        //Act
        toTest.register(userRegisterDto);

        //Assert
        verify(mockUserRepository).saveAndFlush(userCaptor.capture());

        User actualUser = userCaptor.getValue();

        Assertions.assertEquals(userRegisterDto.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(userRegisterDto.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(userRegisterDto.getFirstName(), actualUser.getFirstName());
        Assertions.assertEquals(userRegisterDto.getLastName(), actualUser.getLastName());
        Assertions.assertEquals(userRegisterDto.getPassword() + userRegisterDto.getPassword(), actualUser.getPassword());
    }


    @Test
    void testUserRegistration_UsernameAlreadyExists() {
        UserRegisterDto userRegisterDto = createUserRegisterDto();

        toTest.register(userRegisterDto);

        when(mockUserRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(testUser));

        boolean isSuccess = toTest.register(userRegisterDto);

        Assertions.assertFalse(isSuccess);
    }

    @Test
    void testUserRegistration_EmailAlreadyExists() {
        UserRegisterDto userRegisterDto = createUserRegisterDto();

        toTest.register(userRegisterDto);

        when(mockUserRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));

        boolean isSuccess = toTest.register(userRegisterDto);

        Assertions.assertFalse(isSuccess);
    }

    @Test
    void testUserGetCart() {

//        when(mockUserService.getUser()).thenReturn(testUser);
        when(mockUserHelperService.getUser()).thenReturn(testUser);

        Set<CartItemInfoDto> expectedCart = toTest.getCart();

        Set<CartItem> cartItems = testUser.getCartItems();
        Set<CartItemInfoDto> actualCart = cartItems.stream().map(cartItem -> {
            return modelMapper.map(cartItem, CartItemInfoDto.class);
        }).collect(Collectors.toSet());

        for (CartItemInfoDto cartItemInfoDto : expectedCart) {
            Assertions.assertEquals(cartItemInfoDto.getId(), cartItem.getId());
            Assertions.assertEquals(cartItemInfoDto.getProduct(), cartItem.getProduct());
            Assertions.assertEquals(cartItemInfoDto.getQuantity(), cartItem.getQuantity());
            Assertions.assertEquals(cartItemInfoDto.getSize(), cartItem.getSizes());
            Assertions.assertEquals(cartItemInfoDto.getColor(), cartItem.getColors());
        }
//        .setId(11L)
//                                    .setProduct(mockProduct)
//                                    .setQuantity(5)
//                                    .setSizes(new Size().setSizeName(SizeName.M))
//                                    .setColors(new Color().setColorName(ColorName.BLUE))
//                                    .setUser(mockUser)

    }


}
