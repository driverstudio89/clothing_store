package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.web.dto.ClientInfoDto;

public interface OrderService {
    boolean createOrder(ClientInfoDto clientInfoDto);
}
