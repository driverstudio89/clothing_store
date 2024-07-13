package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.CartItemInfoDto;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @ModelAttribute("cartItemData")
    public CartItemInfoDto cartItemInfoDto() {
        return new CartItemInfoDto();
    }

    @ModelAttribute("clientData")
    public ClientInfoDto clientInfoDto() {
        return new ClientInfoDto();
    }


    private boolean isSubmitted;
    @GetMapping("/users/cart/checkout")
    public String viewCheckout(Model model) {
        Set<CartItemInfoDto> cart = userService.getCart();
        ClientInfoDto clientInfo = userService.getClientInfo();
        BigDecimal total = userService.getCartTotal();


        model.addAttribute("cartItems", cart);
        if (!isSubmitted) {
            model.addAttribute("clientData", clientInfo);
        }
        model.addAttribute("total", total);
        model.addAttribute("deliveryOptions", DeliveryType.values());
        model.addAttribute("paymentOptions", PaymentType.values());
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

        orderService.createOrder(clientInfoDto);

        return "redirect:/";

    }

}
