package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.RoleRepository;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.Role;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.model.enums.UserRole;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.CartItemInfoDto;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final RoleRepository roleRepository;

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
        Role role = roleRepository.findByName(UserRole.USER);
        user.getRoles().add(role);
        userRepository.saveAndFlush(user);
        return true;

    }

    @Transactional
    @Override
    public Set<CartItemInfoDto> getCart() {

        User user = getUser();

        Set<CartItemInfoDto> cart = new LinkedHashSet<>();
        user.getCartItems().forEach(cartItem -> {
            CartItemInfoDto cartItemInfoDto = modelMapper.map(cartItem, CartItemInfoDto.class);
            cart.add(cartItemInfoDto);
        });

        return cart;
    }

    @Override
    public User getUser() {
        return userRepository.findById(userHelperService.getUser().getId()).get();
    }


    @Override
    public ClientInfoDto getClientInfo() {
        User user = userHelperService.getUser();
        return modelMapper.map(user, ClientInfoDto.class);
    }

    @Override
    public BigDecimal getCartTotal() {
        BigDecimal total = new BigDecimal(0);
        for (CartItem cartItem : getUser().getCartItems()) {
            int quantity = cartItem.getQuantity();
            BigDecimal price = cartItem.getProduct().getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
        }
        System.out.println();
        return total;
    }



    @Override
    public void initAdmin() {
        User admin = new User();
        Role adminRole = roleRepository.findByName(UserRole.ADMIN);
        admin.setUsername("admin");
        admin.setPassword(this.passwordEncoder.encode("123456"));
        admin.setEmail("admin@mail.bg");
        admin.setFirstName("Admin");
        admin.setLastName("Adminov");
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
    }

    @Override
    public void initUser() {
        User user = new User();
        Role userRole = roleRepository.findByName(UserRole.USER);
        user.setUsername("username");
        user.setPassword(this.passwordEncoder.encode("123456"));
        user.setEmail("mail@mail.bg");
        user.setFirstName("Petar");
        user.setLastName("Petrov");
        user.getRoles().add(userRole);
        userRepository.save(user);
    }

    @Override
    public void addUserData(ClientInfoDto clientInfoDto) {
        User user = getUser();
        user.setPhoneNumber(clientInfoDto.getPhoneNumber());
        user.setAddress(clientInfoDto.getAddress());
        user.setCountry(clientInfoDto.getCountry());
        user.setCity(clientInfoDto.getCity());
        user.setZip(clientInfoDto.getZip());
        userRepository.save(user);
    }

    @Override
    public boolean itemInCartOutOfStock() {
        boolean isInStock = true;
        for (CartItem cartItem : getUser().getCartItems()) {
            if (!cartItem.getProduct().isInStock()) {
                isInStock = false;
            }
        }
        return isInStock;
    }
}
