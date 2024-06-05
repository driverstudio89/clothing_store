package bg.softuni.clothing_store.model.enums;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(targetEntity = CartItem.class, mappedBy = "cart")
    private List<CartItem> cartItems;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime created;

    private LocalDateTime modified;



}
