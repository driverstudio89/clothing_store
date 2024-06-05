package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.model.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private Double subtotal;

    private Double total;

    private String billingAddress;

    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private LocalDate created;

    private LocalDate modified;



    @ManyToOne
    private User user;

    //#######################################



}
