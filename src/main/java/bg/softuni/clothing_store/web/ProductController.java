package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.SubCategory;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.CloudinaryService;
import bg.softuni.clothing_store.service.ProductService;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CartItemService cartItemService;

    public ProductController(ProductService productService, CloudinaryService cloudinaryService, CloudinaryService cloudinaryService1, CartItemService cartService, CartItemService cartItemService) {
        this.productService = productService;

        this.cartItemService = cartItemService;
    }

    @ModelAttribute("productData")
    public AddProductDto addProductDto() {
        return new AddProductDto();
    }

    @ModelAttribute("addToCartData")
    public AddToCartDto addToCartDto() {
        return new AddToCartDto();
    }

    @GetMapping("/administration/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewAddProduct(Model model) {
        model.addAttribute("subCategoryData", SubCategoryType.values());
        SizeName[] sizeValues = SizeName.values();
        ColorName[] colorValues = ColorName.values();
        model.addAttribute("sizeData", sizeValues);
        model.addAttribute("colorData", colorValues);
        return "add-product";
    }

    @PostMapping("/administration/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    public String doAddProduct(@Valid AddProductDto addProductDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @RequestParam("firstImage") MultipartFile firstImage,
                               @RequestParam("secondImage") MultipartFile secondImage,
                               @RequestParam("thirdImage") MultipartFile thirdImage,
                               @RequestParam("fourthImage") MultipartFile fourthImage,
                               @RequestParam("fifthImage") MultipartFile fifthImage) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productData", addProductDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productData", bindingResult);
            return "redirect:/administration/add-product";
        }

        Map<String, MultipartFile> toUploadMap = Map.of
                ("firstImage", firstImage,
                        "secondImage", secondImage,
                        "thirdImage", thirdImage,
                        "fourthImage", fourthImage,
                        "fifthImage", fifthImage);

        productService.addProduct(addProductDto, toUploadMap);
        redirectAttributes.addFlashAttribute("productAddedSuccessfully", true);
        return "redirect:/administration";
    }

    @GetMapping("/products/details/{id}")
    public String viewProductDetails(@PathVariable long id, Model model) {
        ProductShortInfoDto productShortInfoDto = productService.getProductDetails(id);
        model.addAttribute("productDetails", productShortInfoDto);
        return "product-details";
    }

    @PostMapping("/products/add-to-cart/{id}")
    public String addToCart(@PathVariable long id,
                            @Valid AddToCartDto addToCartDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addToCartData", addToCartDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addToCartDto", bindingResult);
            return "redirect:/products/details/" + id;
        }

        if (!cartItemService.addProduct(id, addToCartDto)) {
            redirectAttributes.addFlashAttribute("productAlreadyInCart", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addToCartDto", bindingResult);
            return "redirect:/products/details/" + id;
        }

        return "redirect:/";
    }

    @PostMapping("/products/remove/{id}")
    public String outOfStock(@PathVariable long id) {

        productService.outOfStock(id);

        return "redirect:/administration";
    }

    @GetMapping("/products/{category}")
    public String viewProductsMen(
            @PathVariable String category,
            @RequestParam(required = false, name = "vars") SubCategoryType subCategoryType,
            Model model) {

        List<SubCategoryType> subCategory = null;

        if (!category.equalsIgnoreCase("MEN")) {
            subCategory = Arrays.stream(SubCategoryType.values()).toList();
        }else {
            subCategory = productService.getSubcategoryMen();
        }

        model.addAttribute("category", category);

        if (subCategoryType != null) {
            Set<ProductShortInfoDto> products = productService.getProducts(CategoryType.valueOf(category.toUpperCase()), subCategoryType);
            model.addAttribute("products", products);
            model.addAttribute("subCategoryType", subCategory);
            return "products";
        }

        Set<ProductShortInfoDto> products = productService.getProducts(CategoryType.valueOf(category.toUpperCase()));
        model.addAttribute("products", products);
        model.addAttribute("subCategoryType", subCategory);
        return "products";
    }


}
