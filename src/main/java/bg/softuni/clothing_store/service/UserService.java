package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.web.dto.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface UserService {

    boolean register(UserRegisterDto userRegisterDto);

    User getUser();

    Set<CartItemInfoDto> getCart();

    ClientInfoDto getClientInfo();

    BigDecimal getCartTotal();

    void initAdmin();

    void initUser();

    boolean itemInCartOutOfStock();

    UserProfileDto getUserProfile();

    Set<ProductShortInfoDto> getFavorites();

    void addToFavorite(long id);

    void removeFavorite(long id);
}
