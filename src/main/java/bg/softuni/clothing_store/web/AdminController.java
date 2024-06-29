package bg.softuni.clothing_store.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("administration")
    public String administration(Model model) {
        return "administration";
    }

}
