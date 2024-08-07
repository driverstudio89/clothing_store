package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.model.enums.StatusType;
import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.service.exception.ObjectNotFoundException;
import bg.softuni.clothing_store.web.dto.CartItemInfoDto;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import bg.softuni.clothing_store.web.dto.OrderInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
            return "redirect:/users/cart/checkout";
        }

        orderService.createOrder(clientInfoDto);
        System.out.println();
        return "redirect:/users/orders";
    }

    @GetMapping("/administration/orders/all-orders")
    @PreAuthorize("hasRole('ADMIN')")
    public String allOrders(
            @RequestParam(required = false) LocalDate created,
            @RequestParam(required = false) StatusType statusType,
            Model model) {

        if (created != null && statusType != null) {
            List<OrderInfoDto> allOrders = orderService.getAllOrders(created, statusType);
            model.addAttribute("allOrders", allOrders);
            model.addAttribute("statusTypes", StatusType.values());
            return "all-orders";
        }
        if (created != null) {
            List<OrderInfoDto> allOrders = orderService.getAllOrders(created);
            model.addAttribute("allOrders", allOrders);
            model.addAttribute("statusTypes", StatusType.values());
            return "all-orders";
        }

        if (statusType != null) {
            List<OrderInfoDto> allOrders = orderService.getAllOrders(statusType);
            model.addAttribute("allOrders", allOrders);
            model.addAttribute("statusTypes", StatusType.values());
            return "all-orders";
        }

        List<OrderInfoDto> allOrders = orderService.getAllOrders();


        model.addAttribute("allOrders", allOrders);
        model.addAttribute("statusTypes", StatusType.values());
        System.out.println();
        return "all-orders";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleException(ObjectNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("/error/order-not-found");
        modelAndView.addObject("id", onfe.getId());
        return modelAndView;
    }

    @GetMapping("/administration/orders/order-{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String orderDetail(@PathVariable long id, Model model) {

        OrderInfoDto orderDetails = orderService.getOrderDetails(id);

        boolean newOrder = orderDetails.getStatus().getName().equals(StatusType.NEW);
        boolean processingOrder = orderDetails.getStatus().getName().equals(StatusType.PROCESSING);

        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderTotal", orderDetails.getTotal());
        model.addAttribute("newOrder", newOrder);
        model.addAttribute("processingOrder", processingOrder);


        System.out.println();

        return "order-details";
    }

    @GetMapping("/administration/orders/processing{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String changeStatusProcessing(@PathVariable long id) {
        orderService.changeStatus(id, StatusType.PROCESSING);

        return "redirect:/administration/orders/all-orders";
    }

    @GetMapping("/administration/orders/complete{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String changeStatusCompleted(@PathVariable long id) {
        orderService.changeStatus(id, StatusType.COMPLETED);

        return "redirect:/administration/orders/all-orders";
    }

}
