package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.UserLoginDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @ModelAttribute("loginData")
    public UserLoginDto userLoginDto() {
        return new UserLoginDto();
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

//    @PostMapping("/users/login")
//    public String doLogin(
//            @Valid UserLoginDto userLoginDto,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes) {
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("loginData", userLoginDto);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
//            return "redirect:login";
//        }
//        if (!userService.login(userLoginDto)) {
//            redirectAttributes.addFlashAttribute("loginData", userLoginDto);
//            redirectAttributes.addFlashAttribute("wrongCredentials", true);
//            return "redirect:login";
//        }
//
//        return "redirect:/";
//    }

}
