package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.ProductService;
import bg.softuni.clothing_store.service.ReviewService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.service.exception.ObjectNotFoundException;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CartItemService cartItemService;
    private final ReviewService reviewService;
    private final UserHelperService userHelperService;
    private final UserService userService;


    @ModelAttribute("productData")
    public AddProductDto addProductDto() {
        return new AddProductDto();
    }

    @ModelAttribute("addToCartData")
    public AddToCartDto addToCartDto() {
        return new AddToCartDto();
    }

    @ModelAttribute("reviewInfoDto")
    public ReviewInfoDto reviewInfoDto() {
        return new ReviewInfoDto();
    }

    @ModelAttribute("reviewData")
    public AddReviewDto addReviewDto() {
        return new AddReviewDto();
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

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleException(ObjectNotFoundException onfe) {
        return new ModelAndView("/error/product-not-found");
    }

    @GetMapping("/products/details/{id}")
    public String viewProductDetails(@PathVariable long id, Model model) {
        ProductShortInfoDto productShortInfoDto = productService.getProductDetails(id);
        Set<ReviewInfoDto> reviewInfoDto = reviewService.getAllReviewsByProduct(id);
        String rating = productService.getRating(id);
        User user = userHelperService.getUser();
        model.addAttribute("productDetails", productShortInfoDto);
        model.addAttribute("reviewInfoDto", reviewInfoDto);
        model.addAttribute("currentUser", user);
        model.addAttribute("rating", rating);
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
    @PreAuthorize("hasRole('ADMIN')")
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
        } else {
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

    @GetMapping("/products/add-review/{id}")
    public String viewAddReview(@PathVariable long id, Model model) {
        String productImage = productService.getProductImage(id);

        model.addAttribute("productImage", productImage);
        model.addAttribute("productId", id);

        return "add-review";
    }

    @PostMapping("/products/add-review/{id}")
    public String addReview(@PathVariable long id,
                            @Valid AddReviewDto addReviewDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewData", addReviewDto());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewData", bindingResult);
            return "redirect:/products/add-review/" + id;
        }

        if (!reviewService.addReview(addReviewDto, id)) {
            return "redirect:/products/add-review/" + id;
        }

        return "redirect:/users/orders";
    }

    @DeleteMapping("/products/removeReview/{id}")
    public String removeReview(@PathVariable long id, HttpServletRequest request) {

        reviewService.removeReview(id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @PostMapping("/users/add-to-favorites/{id}")
    public String addToFavorites(@PathVariable long id, RedirectAttributes redirectAttributes) {

        userService.addToFavorite(id);

        redirectAttributes.addFlashAttribute("toFavorite", true);

        System.out.println();
        return "redirect:/products/details/" + id;
    }

}
