package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.data.*;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.service.CartItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerIT {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CartItemRepository cartItemRepository;


    @Test
    @WithMockUser(username = "testUsername", roles = {"USER"})
    void testRemoveFromCart() throws Exception {

        User user = userRepository.save(new User()
                        .setUsername("testUsername")
                        .setEmail("pesho@mail.com")
                        .setFirstName("Petar")
                        .setLastName("Petrov")
                        .setPassword("secretpass"))
                .setCartItems(new HashSet<>());
        userRepository.save(user);

        Size size = sizeRepository.findBySizeName(SizeName.M);
        Color color = colorRepository.findByColorName(ColorName.BLUE);
        Category category = categoryRepository.findByCategory(CategoryType.MEN);
        SubCategory subCategory = subCategoryRepository.findBySubCategory(SubCategoryType.JACKET);

        Product testProduct = new Product()
                .setName("testProduct")
                .setPrice(BigDecimal.valueOf(15.55))
                .setDescription("testDescription")
                .setSize(Set.of(size))
                .setColor(Set.of(color))
                .setQuantity(2)
                .setImages(List.of("firstImage", "secondImage"))
                .setCreated(LocalDate.now())
                .setModified(LocalDate.now())
                .setCategory(category)
                .setSubCategory(subCategory)
                .setRating("4.44")
                .setStars(40)
                .setVoted(8);
        productRepository.save(testProduct);

        CartItem cartItem = new CartItem()
                .setProduct(testProduct)
                .setQuantity(2)
                .setUser(user)
                .setColors(color)
                .setSizes(size);
        cartItemRepository.save(cartItem);

        user.setCartItems(Set.of(cartItem));
        userRepository.save(user);

        CartItem actualCartItem = cartItemRepository.findByProductIdAndUserId(testProduct.getId(), user.getId()).get();

        mockMvc.perform(get(String.format("/users/cart/remove/%d", actualCartItem.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/cart"));

        Assertions.assertFalse(cartItemRepository.existsById(actualCartItem.getId()));
    }





}
