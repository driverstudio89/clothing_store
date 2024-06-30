package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.service.CloudinaryService;
import bg.softuni.clothing_store.service.ProductService;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    public ProductController(ProductService productService, CloudinaryService cloudinaryService, CloudinaryService cloudinaryService1) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService1;
    }

    @ModelAttribute("productData")
    public AddProductDto addProductDto() {
        return new AddProductDto();
    }

    @GetMapping("/administration/add-product")
    public String viewAddProduct(Model model) {
        model.addAttribute("subCategoryData", SubCategoryType.values());
        return "add-product";
    }

    @PostMapping("/administration/add-product")
    public String doAddProduct(@Valid AddProductDto addProductDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @RequestParam("firstImage") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productData", addProductDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productData", bindingResult);
            return "redirect:/administration/add-product";
        }

        String imageUrl = cloudinaryService.uploadFile(file);
        productService.addProduct(addProductDto, imageUrl);
        redirectAttributes.addFlashAttribute("productAddedSuccessfully", true);
        return "redirect:/administration";


    }

}
