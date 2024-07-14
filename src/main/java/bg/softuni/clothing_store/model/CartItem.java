package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.web.dto.AddToCartDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Product.class)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(targetEntity = Size.class)
    private Size sizes;

    @ManyToOne(targetEntity = Color.class)
    private Color colors;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public CartItem() {
    }
}
