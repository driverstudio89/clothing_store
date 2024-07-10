package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.Color;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemInfoDto {
    private long id;
    private Product product;
    private int quantity;
    private Size size;
    private Color color;


}
