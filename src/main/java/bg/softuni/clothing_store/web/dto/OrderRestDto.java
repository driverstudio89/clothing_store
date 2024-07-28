package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.OrderItem;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.model.enums.StatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRestDto {
    private List<OrderItemDto> orderItemsRest;

    private BigDecimal total;

    private PaymentType paymentType;

    private DeliveryType deliveryType;

//    private LocalDateTime created;
//
//    private LocalDateTime modified;

    private Long user;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String Address;

    private String country;

    private String city;

    private String zip;

}
