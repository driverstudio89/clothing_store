package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import bg.softuni.clothing_store.web.dto.CartItemInfoDto;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        Double total = userService.getCartTotal();

        System.out.println();
        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);
        model.addAttribute("quantity", cartItemInfoDto().getQuantity());

        return "cart";
    }

    @GetMapping("/users/cart/remove/{id}")
    public String removeFromCart(@PathVariable long id) {
        cartItemService.removeFromCart(id);

        return "redirect:/users/cart";
    }

    private boolean isSubmitted;
    @GetMapping("/users/cart/checkout")
    public String viewCheckout(Model model) {
        Set<CartItemInfoDto> cart = userService.getCart();
        ClientInfoDto clientInfo = userService.getClientInfo();
        Double total = userService.getCartTotal();


        model.addAttribute("cartItems", cart);
        if (!isSubmitted) {
            model.addAttribute("clientData", clientInfo);
        }
        model.addAttribute("total", total);
        isSubmitted = false;
        return "checkout";

    }

    @PostMapping("/users/cart/checkout")
    public String doCheckout(
            @Valid ClientInfoDto clientInfoDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("clientData", clientInfoDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clientData", bindingResult);
            isSubmitted = true;
            return  "redirect:/users/cart/checkout";
        }

        return "redirect:/users/cart";

    }

}
