package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.model.enums.StatusType;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import bg.softuni.clothing_store.web.dto.OrderInfoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public interface OrderService {
    boolean createOrder(ClientInfoDto clientInfoDto);


    OrderInfoDto getOrderDetails(long id);

//    BigDecimal getOrderTotal(long id);

    void changeStatus(long id, StatusType statusType);

    List<OrderInfoDto> getAllOrders();

    List<OrderInfoDto> getAllOrders(LocalDate created);

    List<OrderInfoDto> getAllOrders(StatusType statusType);

    List<OrderInfoDto> getAllOrders(LocalDate created, StatusType statusType);

    List<OrderInfoDto> allUserOrders();

    void deleteOrderFromUser(long id);
}
