package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.config.CurrentUser;
import bg.softuni.clothing_store.data.CartItemRepository;
import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;


    public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository, CurrentUser currentUser) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addProduct(long id, AddToCartDto addToCartDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product with ID: " + id + "not found");
        }
        Product product = optionalProduct.get();
        User user = currentUser.getUser();
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
