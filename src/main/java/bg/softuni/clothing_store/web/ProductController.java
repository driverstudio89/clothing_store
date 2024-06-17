package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.service.ProductService;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/administration/add-product")
    public String addProduct(@Valid AddProductDto addProductDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductDto", bindingResult);
            redirectAttributes.addFlashAttribute("productData", addProductDto);
            return "redirect:/administration/add-product";
        }

        productService.addProduct(addProductDto);
        redirectAttributes.addFlashAttribute("productAddedSuccessfully", true);
        return "redirect:/administration";


    }

}
