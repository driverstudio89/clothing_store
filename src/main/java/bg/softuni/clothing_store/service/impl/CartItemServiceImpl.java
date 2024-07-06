package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.CartItemRepository;
import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.UserHelperService;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserHelperService userHelperService;

    @Override
    public boolean addProduct(long id, AddToCartDto addToCartDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product with ID: " + id + "not found");
        }
        Product product = optionalProduct.get();
        User user = userHelperService.getUser();
        CartItem cartItem = new CartItem(product, addToCartDto);
        cartItem.setUser(user);
        cartItemRepository.saveAndFlush(cartItem);
        return true;
    }

    @Override
    public void removeFromCart(long id) {
        CartItem cartItem = cartItemRepository.findById(id).get();
        cartItemRepository.deleteById(id);

    }
}
