package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.config.CurrentUser;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.UserLoginDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        User user = modelMapper.map(userRegisterDto, User.class);

        user.setPassword(this.passwordEncoder.encode(userRegisterDto.getPassword()));

        userRepository.saveAndFlush(user);

    }

    @Override
    public void login(UserLoginDto loginData) {
        User user = userRepository.findByUsername(loginData.getUsername());

        if (user == null) {
            //TODO throw
        }
        if (passwordEncoder.matches(loginData.getPassword(), user.getPassword()) && !currentUser.isLoggedIn()) {
            currentUser.setUser(user);
            System.out.println();
        }

    }

    @Override
    public void logout() {
        currentUser.setUser(null);
    }
}
