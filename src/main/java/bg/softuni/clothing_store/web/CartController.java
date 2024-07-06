package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
public class CartController {
    private final CartItemService cartItemService;
    private final UserService userService;

    public CartController(CartItemService cartItemService, UserService userService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
    }


    @GetMapping("/users/cart")
    public String viewCart(Model model) {
        Set<CartItem> cart = userService.getCart();
        Double total = 0.0;
        for (CartItem cartItem : cart) {
            Double price = cartItem.getProduct().getPrice();
            total += price;
        }
        System.out.println();
        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping("/products/add-to-cart/{id}")
    public String AddToCart(@PathVariable long id,
                            @Valid AddToCartDto addToCartDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addToCartDto", addToCartDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addToCartDto", bindingResult);
            return "redirect:/products/details/" + id;
        }

        cartItemService.addProduct(id, addToCartDto);

        return "redirect:/";
    }

    @GetMapping("/users/cart/remove/{id}")
    public String removeFromCart(@PathVariable long id) {
        cartItemService.removeFromCart(id);

        return "redirect:/users/cart";
    }



}
