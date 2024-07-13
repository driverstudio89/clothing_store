package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.Color;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemInfoDto {

    private long id;
    @NotNull
    private Product product;

    @NotNull
    private boolean isInStock;

    @NotNull
    @Positive(message = "Quantity must be positive number!")
    private int quantity;
    @NotNull
    private Size size;
    @NotNull
    private Color color;


}
