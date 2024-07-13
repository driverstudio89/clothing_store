package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class InitUsers {

    private final UserService userService;
    private final UserRepository userRepository;

    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }

        userService.initAdmin();
        userService.initUser();
    }
}
