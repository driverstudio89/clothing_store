package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.config.CurrentUser;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.UserLoginDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
    public boolean register(UserRegisterDto userRegisterDto) {
        Optional<User> byUsername = userRepository.findByUsername(userRegisterDto.getUsername());
        if (byUsername.isPresent()) {
            return false;
        }

        Optional<User> byEmail = userRepository.findByEmail(userRegisterDto.getEmail());
        if (byEmail.isPresent()) {
            return false;
        }

        User user = modelMapper.map(userRegisterDto, User.class);

        user.setPassword(this.passwordEncoder.encode(userRegisterDto.getPassword()));

        userRepository.saveAndFlush(user);

        return true;

    }

    @Override
    public boolean login(UserLoginDto loginData) {
        Optional<User> byUsername = userRepository.findByUsername(loginData.getUsername());


        if (byUsername.isEmpty()) {
            return false;
        }
        User user = byUsername.get();
        if (passwordEncoder.matches(loginData.getPassword(), user.getPassword()) && !currentUser.isLoggedIn()) {
            currentUser.setUser(user);
            return true;
        }

        return false;

    }

    @Override
    public void logout() {
        currentUser.setUser(null);
    }

    @Override
    public Set<CartItem> getCart() {

        User user = userRepository.findById(currentUser.getUser().getId()).get();
        Set<CartItem> cartItems = user.getCartItems();
        return cartItems;

    }
}
