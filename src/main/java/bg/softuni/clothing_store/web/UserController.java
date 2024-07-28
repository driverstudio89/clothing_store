package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.OrderInfoDto;
import bg.softuni.clothing_store.web.dto.UserLoginDto;
import bg.softuni.clothing_store.web.dto.UserProfileDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @ModelAttribute("registerData")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @ModelAttribute("loginData")
    public UserLoginDto userLoginDto() {
        return new UserLoginDto();
    }

    @ModelAttribute("profileData")
    public UserProfileDto userProfileDto() {
        return new UserProfileDto();
    }

    @ModelAttribute("orderDetails")
    public OrderInfoDto orderInfoDto() {
        return new OrderInfoDto();
    }

    @GetMapping("/users/register")
    public String viewRegister() {
        return "register";
    }

    @PostMapping("/users/register")
    public String registerConfirm(@Valid UserRegisterDto userRegisterDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            return "redirect:register";
        }
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            redirectAttributes.addFlashAttribute("passwordMismatch", true);
            return "redirect:register";
        }

        if (!userService.register(userRegisterDto)) {
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            redirectAttributes.addFlashAttribute("registrationFailed", true);
            return "redirect:register";
        }


        return "redirect:login";
    }

    @GetMapping("/users/login")
    public String viewLogin() {
        return "login";
    }

    @GetMapping("/users/login-error")
    public String viewLoginError(Model model) {
        model.addAttribute("wrongCredentials", true);

        return "login";
    }

    @GetMapping("/users/profile")
    public String viewProfile(Model model) {
        UserProfileDto userProfileDto = userService.getUserProfile();
        model.addAttribute("profileData", userProfileDto);
        return "profile";
    }

    @GetMapping("/users/orders")
    public String viewUserOrders(Model model) {

        List<OrderInfoDto> allOrders = orderService.allUserOrders();

        model.addAttribute("allOrders", allOrders);
        return "user-orders";
    }

    @Transactional
    @DeleteMapping("/users/orders/delete/{id}")
    public String deleteUserOrder(@PathVariable long id) {
        orderService.deleteOrderFromUser(id);
        return "redirect:/users/orders";
    }


}
