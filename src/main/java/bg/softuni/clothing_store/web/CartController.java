package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import bg.softuni.clothing_store.web.dto.CartItemInfoDto;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartItemService cartItemService;
    private final UserService userService;

    @ModelAttribute("addToCartData")
    public AddToCartDto addToCartData() {
        return new AddToCartDto();
    }

    @ModelAttribute("cartItemData")
    public CartItemInfoDto cartItemInfoDto() {
        return new CartItemInfoDto();
    }

    @ModelAttribute("clientData")
    public ClientInfoDto clientInfoDto() {
        return new ClientInfoDto();
    }


    @GetMapping("/users/cart")
    public String viewCart(Model model) {
        Set<CartItemInfoDto> cart = userService.getCart();
        BigDecimal total = userService.getCartTotal();
        boolean isInStock = userService.itemInCartOutOfStock();


        System.out.println();
        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);
        model.addAttribute("quantity", cartItemInfoDto().getQuantity());
        model.addAttribute("isInStock", isInStock);

        return "cart";
    }


    @GetMapping("/users/cart/remove/{id}")
    public String removeFromCart(@PathVariable long id) {
        cartItemService.removeFromCart(id);

        return "redirect:/users/cart";
    }





}
