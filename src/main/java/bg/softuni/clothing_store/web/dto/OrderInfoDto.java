package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.OrderItem;
import bg.softuni.clothing_store.model.Status;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class OrderInfoDto {

    private Long id;

    private List<OrderItem> orderItems;

    private BigDecimal total;

    private Status status;

    private PaymentType paymentType;

    private DeliveryType deliveryType;

    private LocalDateTime created;

    private LocalDateTime modified;

    private User user;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phoneNumber;

    private String country;

    private String city;

    private String zip;

}
