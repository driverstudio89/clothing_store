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

    @OneToOne
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @ManyToMany
    private List<Size> sizes;

    @ManyToMany
    private List<Color> colors;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public CartItem() {
        this.sizes = new ArrayList<>();
        this.colors = new ArrayList<>();
    }
}
