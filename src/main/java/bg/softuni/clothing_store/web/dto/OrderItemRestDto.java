package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemRestDto {

    private Long id;

    private Long productId;

    private int quantity;

    private SizeName sizes;

    private ColorName colors;


}
