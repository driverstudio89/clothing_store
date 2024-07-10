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

//        Set<CartItemInfoDto> cart = new LinkedHashSet<>();
//        user.getCartItems().forEach(cartItem -> {
//            CartItemInfoDto cartItemInfoDto = new CartItemInfoDto();
//            modelMapper.map(cartItem, CartItemInfoDto.class);
//            cart.add(cartItemInfoDto);
//        });


        Set<CartItemInfoDto> cart = new LinkedHashSet<>();
        for (CartItem cartItem : user.getCartItems()) {
            CartItemInfoDto cartItemInfoDto = new CartItemInfoDto();
            cartItemInfoDto.setId(cartItem.getId());
            cartItemInfoDto.setProduct(cartItem.getProduct());
            cartItemInfoDto.setQuantity(cartItem.getQuantity());
            cartItemInfoDto.setColor(cartItem.getColors().getFirst());
            cartItemInfoDto.setSize(cartItem.getSizes().getFirst());
            cart.add(cartItemInfoDto);
        }
        return cart;
    }

    private User getUser() {
        return userRepository.findById(userHelperService.getUser().getId()).get();
    }


    @Override
    public ClientInfoDto getClientInfo() {
        User user = userHelperService.getUser();
        return modelMapper.map(user, ClientInfoDto.class);
    }

    @Override
    public Double getCartTotal() {
        Double total = 0.0;
        for (CartItem cartItem : getUser().getCartItems()) {
            Double price = cartItem.getProduct().getPrice();
            total += price;
        }
        return total;
    }
}
