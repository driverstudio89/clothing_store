package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class OrderInfoRestDto {

    private Long id;

    private List<OrderItemRestDto> orderItemsRest;

    private Long user;

    private BigDecimal total;

    private String Address;

    private StatusType status;

    private PaymentType paymentType;

    private DeliveryType deliveryType;

    private LocalDateTime created;

    private LocalDateTime modified;

    public OrderInfoRestDto() {
        this.orderItemsRest = new ArrayList<>();
    }
}
