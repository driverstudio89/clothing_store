package bg.softuni.clothing_store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    private Size sizes;

    @ManyToOne
    private Color colors;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public OrderItem() {
    }


}
