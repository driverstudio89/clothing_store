package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.UserLoginDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/register")
    public String viewRegister(Model model) {
        model.addAttribute("registerData", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/users/register")
    public String registerConfirm(@Valid UserRegisterDto userRegisterDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            return "redirect:register";
        }

        userService.register(userRegisterDto);
        return "redirect:login";
    }

    @GetMapping("users/login")
    public String viewLogin(Model model) {
        model.addAttribute("loginData", new UserLoginDto());
        return "login";
    }

    @PostMapping("/users/login")
    public String login(UserLoginDto loginData) {
        userService.login(loginData);

        return "redirect:/";
    }

    @PostMapping("/users/logout")
    public String logout() {
        userService.logout();
        return "redirect:/";
    }
}
