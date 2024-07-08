package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AddToCartDto {

    @Positive(message = "You need to select Quantity!")
    @Min(value = 1, message = "You need to select Quantity!")
    @Max(value = 20, message = "If you want to buy more than this, contact us!")
    private int quantity;

    @NotNull(message = "You must select valid size!")
    private SizeName size;

    @NotNull(message = "You must select valid size!")
    private ColorName color;

}
