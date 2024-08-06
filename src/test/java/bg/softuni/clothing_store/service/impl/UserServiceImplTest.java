package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.RoleRepository;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.model.enums.UserRole;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.*;
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

import java.math.BigDecimal;
import java.util.HashSet;
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

    @Mock
    private Product mockProductInStock = new Product()
            .setId(11L)
            .setPrice(BigDecimal.valueOf(45.50))
            .setInStock(true);

    @Mock
    private Product mockProductOutOfStock = new Product()
            .setId(11L)
            .setInStock(false);

    @Mock
    private User mockUser;

    private final CartItem cartItem = new CartItem()
            .setId(11L)
            .setProduct(mockProductInStock)
            .setQuantity(5)
            .setSizes(new Size().setSizeName(SizeName.M))
            .setColors(new Color().setColorName(ColorName.BLUE))
            .setUser(mockUser);

    private final CartItem cartItemOutOfStock = new CartItem()
            .setProduct(mockProductOutOfStock);

    private final User userWithProductOutOfStock = new User()
            .setCartItems(Set.of(cartItemOutOfStock));

    private final User userWithFavorites = new User()
            .setFavorites(new HashSet<>());

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
            )
            .setPhoneNumber("123456789")
            .setAddress("address 21")
            .setCountry("Bulgaria")
            .setCity("Sliven")
            .setZip("1111")
            .setFavorites(Set.of(mockProductInStock))
            ;

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
                mockRoleRepository,
                mockProductRepository,
                mockUserHelperService,
                mockPasswordEncoder,
                modelMapper
                );
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
    }

    @Test
    void getClientInfo() {

        when(mockUserHelperService.getUser()).thenReturn(testUser);
        ClientInfoDto clientInfoDto = toTest.getClientInfo();

        Assertions.assertEquals(clientInfoDto.getId(), testUser.getId());
        Assertions.assertEquals(clientInfoDto.getFirstName(), testUser.getFirstName());
        Assertions.assertEquals(clientInfoDto.getLastName(), testUser.getLastName());
        Assertions.assertEquals(clientInfoDto.getEmail(), testUser.getEmail());
        Assertions.assertEquals(clientInfoDto.getPhoneNumber(), testUser.getPhoneNumber());
        Assertions.assertEquals(clientInfoDto.getAddress(), testUser.getAddress());
        Assertions.assertEquals(clientInfoDto.getCountry(), testUser.getCountry());
        Assertions.assertEquals(clientInfoDto.getCity(), testUser.getCity());
        Assertions.assertEquals(clientInfoDto.getZip(), testUser.getZip());

    }

    @Test
    void getCartTotal() {

        when(mockUserHelperService.getUser()).thenReturn(testUser);

        BigDecimal  actualTotal= toTest.getCartTotal();

        BigDecimal expectedTotal = new BigDecimal(0);

        for (CartItem cartItem : testUser.getCartItems()) {
            BigDecimal price = cartItem.getProduct().getPrice();
            BigDecimal total = price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            expectedTotal = expectedTotal.add(total);
        }
        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void initAdmin() {

        when(mockRoleRepository.findByName(UserRole.ADMIN)).thenReturn(new Role().setName(UserRole.ADMIN));
        when(mockPasswordEncoder.encode("123456")).thenReturn("123456");

        toTest.initAdmin();

        verify(mockUserRepository).save(userCaptor.capture());

        User actualUser = userCaptor.getValue();

        Assertions.assertEquals("admin", actualUser.getUsername());
        Assertions.assertEquals(mockPasswordEncoder.encode("123456"), actualUser.getPassword());
        Assertions.assertEquals("admin@mail.bg", actualUser.getEmail());
        Assertions.assertEquals("Admin", actualUser.getFirstName());
        Assertions.assertEquals("Adminov", actualUser.getLastName());
        actualUser.getRoles().forEach(role -> {
            Assertions.assertEquals(UserRole.ADMIN, role.getName());
        });
    }

    @Test
    void initUser() {

        when(mockRoleRepository.findByName(UserRole.USER)).thenReturn(new Role().setName(UserRole.USER));
        when(mockPasswordEncoder.encode("123456")).thenReturn("123456");

        toTest.initUser();

        verify(mockUserRepository).save(userCaptor.capture());

        User actualUser = userCaptor.getValue();

        Assertions.assertEquals("username", actualUser.getUsername());
        Assertions.assertEquals(mockPasswordEncoder.encode("123456"), actualUser.getPassword());
        Assertions.assertEquals("mail@mail.bg", actualUser.getEmail());
        Assertions.assertEquals("Petar", actualUser.getFirstName());
        Assertions.assertEquals("Petrov", actualUser.getLastName());
        actualUser.getRoles().forEach(role -> {
            Assertions.assertEquals(UserRole.USER, role.getName());
        });
    }

    @Test
    void itemInCartOutOfStock_isInStock() {
        when(mockUserHelperService.getUser()).thenReturn(testUser);

        boolean actual = toTest.itemInCartOutOfStock();

        testUser.getCartItems().forEach(c -> {
            Assertions.assertTrue(c.getProduct().isInStock());
        });
    }

    @Test
    void itemInCartOutOfStock_isOutOfStock() {
        when(mockUserHelperService.getUser()).thenReturn(userWithProductOutOfStock);


        boolean actual = toTest.itemInCartOutOfStock();

        userWithProductOutOfStock.getCartItems().forEach(c -> {
            Assertions.assertFalse(c.getProduct().isInStock());
        });
    }

    @Test
    void getUserProfile() {
        when(mockUserHelperService.getUser()).thenReturn(testUser);

        UserProfileDto userProfileDto = toTest.getUserProfile();

        Assertions.assertEquals(userProfileDto.getUsername(), testUser.getUsername());
        Assertions.assertEquals(userProfileDto.getEmail(), testUser.getEmail());
        Assertions.assertEquals(userProfileDto.getFirstName(), testUser.getFirstName());
        Assertions.assertEquals(userProfileDto.getLastName(), testUser.getLastName());
        Assertions.assertEquals(userProfileDto.getPhoneNumber(), testUser.getPhoneNumber());
        Assertions.assertEquals(userProfileDto.getAddress(), testUser.getAddress());
        Assertions.assertEquals(userProfileDto.getCountry(), testUser.getCountry());
        Assertions.assertEquals(userProfileDto.getCity(), testUser.getCity());
        Assertions.assertEquals(userProfileDto.getZip(), testUser.getZip());
    }

    @Test
    void getFavorites() {
        when(mockUserHelperService.getUser()).thenReturn(testUser);

        Set<ProductShortInfoDto> actualFavorites = toTest.getFavorites();
        Set<Product> expectedFavorites = testUser.getFavorites();

        Assertions.assertEquals(expectedFavorites.size(), actualFavorites.size());
        expectedFavorites.forEach(p -> {
            Assertions.assertEquals(p.getId(), actualFavorites.iterator().next().getId());
        });

    }

    @Test
    void addToFavorites() {
        Long id = mockProductInStock.getId();
        Product expectedProduct = new Product().setId(11L);

        when(mockUserHelperService.getUser()).thenReturn(userWithFavorites);
        when(mockProductRepository.findById(id)).thenReturn(Optional.ofNullable(expectedProduct));

        toTest.addToFavorite(id);

        verify(mockUserRepository).save(userCaptor.capture());

        User actualUser = userCaptor.getValue();
        Assertions.assertEquals(expectedProduct, actualUser.getFavorites().stream().findFirst().get());
    }

    @Test
    void removeFavorite() {
        Long id = mockProductInStock.getId();
        userWithFavorites.getFavorites().add(mockProductInStock);
        when(mockProductRepository.findById(id)).thenReturn(Optional.ofNullable(mockProductInStock));

        when(mockUserHelperService.getUser()).thenReturn(userWithFavorites);
        toTest.removeFavorite(id);

        verify(mockUserRepository).save(userCaptor.capture());
        User actualUser = userCaptor.getValue();
        Assertions.assertFalse(userWithFavorites.getFavorites().contains(mockProductInStock));
    }




}
