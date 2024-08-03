package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.web.dto.AddToCartDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_items")
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

    //#################################################

    public CartItem() {
    }

    public Long getId() {
        return id;
    }

    public CartItem setId(Long id) {
        this.id = id;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public CartItem setProduct(Product product) {
        this.product = product;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Size getSizes() {
        return sizes;
    }

    public CartItem setSizes(Size sizes) {
        this.sizes = sizes;
        return this;
    }

    public Color getColors() {
        return colors;
    }

    public CartItem setColors(Color colors) {
        this.colors = colors;
        return this;
    }

    public User getUser() {
        return user;
    }

    public CartItem setUser(User user) {
        this.user = user;
        return this;
    }
}
