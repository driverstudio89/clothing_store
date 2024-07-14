package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.model.enums.StatusType;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import bg.softuni.clothing_store.web.dto.OrderInfoDto;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public interface OrderService {
    boolean createOrder(ClientInfoDto clientInfoDto);

    LinkedHashSet<OrderInfoDto> getAllOrders();


    OrderInfoDto getOrderDetails(long id);

    BigDecimal getOrderTotal(long id);

    void changeStatus(long id, StatusType statusType);
}
