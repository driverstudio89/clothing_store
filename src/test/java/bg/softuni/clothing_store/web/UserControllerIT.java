package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.data.RoleRepository;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.model.enums.UserRole;
import bg.softuni.clothing_store.web.dto.UserProfileDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testRegistration_Success() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "testUsername")
                        .param("email", "pesho@mail.com")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "123456")
                        .param("confirmPassword", "123456")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"));

        Optional<User> optionalUser = userRepository.findByUsername("testUsername");

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        Assertions.assertEquals("pesho@mail.com", user.getEmail());
        Assertions.assertEquals("Petar", user.getFirstName());
        Assertions.assertEquals("Petrov", user.getLastName());
        Assertions.assertTrue(passwordEncoder.matches("123456", user.getPassword()));
    }

    @Test
    void testRegistration_bindingErrors() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "t")
                        .param("email", "p")
                        .param("firstName", "P")
                        .param("lastName", "P")
                        .param("password", "1")
                        .param("confirmPassword", "1")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("register"));

        Optional<User> optionalUser = userRepository.findByUsername("testUsername");
        Assertions.assertTrue(optionalUser.isEmpty());
    }

    @Test
    void testRegistration_passwordMismatch() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "testUsername")
                        .param("email", "pesho@mail.com")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "654321")
                        .param("confirmPassword", "123456")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("register"));
        Optional<User> optionalUser = userRepository.findByUsername("testUsername");
        Assertions.assertTrue(optionalUser.isEmpty());
    }

    @Test
    void testRegistration_UsernameAlreadyInUse() throws Exception {
        userRepository.save(new User()
                .setUsername("testUsername")
                .setEmail("pesho@mail.com")
                .setFirstName("Petar")
                .setLastName("Petrov")
                .setPassword("secretpass"));

        mockMvc.perform(post("/users/register")
                        .param("username", "testUsername")
                        .param("email", "unique@mail.com")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "123456")
                        .param("confirmPassword", "123456")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("register"));
    }

    @Test
    void testRegistration_EmailAlreadyInUse() throws Exception {
        userRepository.save(new User()
                .setUsername("testUsername")
                .setEmail("pesho@mail.com")
                .setFirstName("Petar")
                .setLastName("Petrov")
                .setPassword("secretpass"));

        mockMvc.perform(post("/users/register")
                        .param("username", "uniqueUsername")
                        .param("email", "pesho@mail.com")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("password", "123456")
                        .param("confirmPassword", "123456")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("register"));
    }

    @Test
    void testViewRegistration() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testViewLogin() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testViewLoginError() throws Exception {
        mockMvc.perform(get("/users/login-error"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("wrongCredentials", true));
    }

    @Test
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testViewProfile() throws Exception {
        User user = userRepository.save(new User()
                        .setUsername("testUsername")
                        .setEmail("pesho@mail.com")
                        .setFirstName("Petar")
                        .setLastName("Petrov")
                        .setPassword("secretpass"))
                .setRoles(List.of(roleRepository.findByName(UserRole.USER)));

        UserProfileDto userProfileDto = new UserProfileDto()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());

                mockMvc.perform(get("/users/profile"))
                .andExpect(status().is2xxSuccessful())
                        .andExpect(model().attributeExists("profileData"));
    }


}
