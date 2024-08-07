package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class OrderItemDto {

    private Long productId;

    private int quantity;

    private SizeName sizes;

    private ColorName colors;

    private Long userId;

    public Long getProductId() {
        return productId;
    }

    public OrderItemDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItemDto setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public SizeName getSizes() {
        return sizes;
    }

    public OrderItemDto setSizes(SizeName sizes) {
        this.sizes = sizes;
        return this;
    }

    public ColorName getColors() {
        return colors;
    }

    public OrderItemDto setColors(ColorName colors) {
        this.colors = colors;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public OrderItemDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
