package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddProductDto {

    @NotNull
    @Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters!")
    private String name;

    @NotNull
    @Size(min = 3, max = 1000, message = "Description length must be between 3 and 1000 characters!")
    private String description;

    @NotNull(message = "You must input price!")
    @Positive(message = "Price must be positive!")
    private Double price;

    @NotNull(message = "You must input quantity!")
    @Positive(message = "Quantity must be positive!")
    private int quantity;

    @NotBlank(message = "You must choice a color!")
    private String color;

    @NotBlank(message = "You must choice a size!")
    private String size;

    @NotBlank(message = "You must choice a category!")
    private String category;

    @NotBlank(message = "You must choice a type!")
    private String subCategory;

    //#############################################################


    public @NotNull @Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters!") String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters!") String name) {
        this.name = name;
    }

    public @NotNull @Size(min = 3, max = 1000, message = "Description length must be between 3 and 1000 characters!") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @Size(min = 3, max = 1000, message = "Description length must be between 3 and 1000 characters!") String description) {
        this.description = description;
    }

    public @NotNull @Positive(message = "Price must be positive!") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull @Positive(message = "Price must be positive!") Double price) {
        this.price = price;
    }

    @NotNull
    @Positive(message = "Quantity must be positive!")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull @Positive(message = "Quantity must be positive!") int quantity) {
        this.quantity = quantity;
    }

    public @NotNull(message = "You must choice a color!") String getColor() {
        return color;
    }

    public void setColor(@NotNull(message = "You must choice a color!") String color) {
        this.color = color;
    }

    public @NotNull(message = "You must choice a size!") String getSize() {
        return size;
    }

    public void setSize(@NotNull(message = "You must choice a size!") String size) {
        this.size = size;
    }

    public @NotNull(message = "You must choice a category!") String getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "You must choice a category!") String category) {
        this.category = category;
    }

    public @NotNull(message = "You must choice a type!") String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(@NotNull(message = "You must choice a type!") String subCategory) {
        this.subCategory = subCategory;
    }
}
