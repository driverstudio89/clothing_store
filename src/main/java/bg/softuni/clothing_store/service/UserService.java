package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.web.dto.UserLoginDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;

import java.util.Set;

public interface UserService {

    boolean register(UserRegisterDto userRegisterDto);

    Set<CartItem> getCart();
}
