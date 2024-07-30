package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.service.ProductService;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public String viewHome(Model model) {

        Set<ProductShortInfoDto> lastProducts = productService.getLastProducts();
        model.addAttribute("lastProducts", lastProducts);


        return "index";
    }

    @GetMapping("/about")
    public String viewAbout() {

        return "about";
    }


}
