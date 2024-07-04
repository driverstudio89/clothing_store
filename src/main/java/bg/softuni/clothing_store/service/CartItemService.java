package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.web.dto.AddToCartDto;

public interface CartItemService {
    boolean addProduct(long id, AddToCartDto addToCartDto);

    void removeFromCart(long id);
}
