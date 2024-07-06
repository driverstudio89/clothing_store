package bg.softuni.clothing_store.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddToCartDto {

    private String size;

    private String color;

}
