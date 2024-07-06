package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

}
