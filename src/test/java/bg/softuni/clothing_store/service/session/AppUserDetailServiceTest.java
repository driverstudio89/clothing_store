package bg.softuni.clothing_store.service.session;

import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.Role;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.model.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailServiceTest {

    private static final String TEST_USERNAME = "test_username";
    private static final String TEST_MISSING_USERNAME = "missing_username";

    private AppUserDetailService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new AppUserDetailService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {

        User testUser = new User()
                .setUsername(TEST_USERNAME)
                .setPassword("secretpass")
                .setFirstName("Petar")
                .setLastName("Petrov")
                .setEmail("petar@mail.com")
                .setRoles(List.of(
                        new Role().setName(UserRole.ADMIN),
                        new Role().setName(UserRole.USER)
                ));

        when(mockUserRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testUser));



        UserDetails userDetails = toTest.loadUserByUsername(TEST_USERNAME);

        Assertions.assertEquals(TEST_USERNAME, userDetails.getUsername());
        Assertions.assertEquals("secretpass", userDetails.getPassword());

        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        Optional<? extends GrantedAuthority> admin = userDetails.getAuthorities()
                .stream().filter(a -> "ROLE_ADMIN".equals(a.getAuthority())).findAny();

        Assertions.assertTrue(admin.isPresent());

        Optional<? extends GrantedAuthority> user = userDetails.getAuthorities()
                .stream().filter(a -> "ROLE_USER".equals(a.getAuthority())).findAny();

        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(TEST_MISSING_USERNAME));
    }


}
