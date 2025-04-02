package bg.softuni.clothing_store.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminController {

    @GetMapping("administration")
    @PreAuthorize("hasRole('ADMIN')")
    public String administration(Model model) {
        return "administration";
    }

}
