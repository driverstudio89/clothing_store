package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import bg.softuni.clothing_store.web.dto.OrderInfoDto;

import java.util.LinkedHashSet;
import java.util.Set;

public interface OrderService {
    boolean createOrder(ClientInfoDto clientInfoDto);

    LinkedHashSet<OrderInfoDto> getAllOrders();


}
