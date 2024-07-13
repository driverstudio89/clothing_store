package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity = CartItem.class)
    private Set<CartItem> cartItem;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private String Address;

    @ManyToOne(targetEntity = Status.class)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private LocalDate modified;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Order() {
        this.cartItem = new HashSet<>();
    }

    //#######################################

}
