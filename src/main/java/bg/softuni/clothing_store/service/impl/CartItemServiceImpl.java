package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.CartItemRepository;
import bg.softuni.clothing_store.data.ColorRepository;
import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.SizeRepository;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserHelperService userHelperService;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    @Override
    public boolean addProduct(long productId, AddToCartDto addToCartDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product with ID: " + productId + " not found");
        }
        if (userHelperService.getUser() == null) {
            return false;
        }
        Long userId = userHelperService.getUser().getId();
        Product product = optionalProduct.get();
        if (cartItemRepository.findByProductIdAndUserId(productId, userId).isPresent()) {
            return false;
        }

        User user = userHelperService.getUser();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
//        cartItem.setUser(user);


        Size size = sizeRepository.findBySizeName(addToCartDto.getSize());
        cartItem.setSizes(size);


        Color color = colorRepository.findByColorName(addToCartDto.getColor());
        cartItem.setColors(color);

        cartItem.setQuantity(addToCartDto.getQuantity());


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
