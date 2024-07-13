package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.OrderRepository;
import bg.softuni.clothing_store.data.StatusRepository;
import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.Order;
import bg.softuni.clothing_store.model.Status;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.model.enums.StatusType;
import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final StatusRepository statusRepository;

    @Override
    @Transactional
    public boolean createOrder(ClientInfoDto clientInfoDto) {
        userService.addUserData(clientInfoDto);
        Set<CartItem> cart = userService.getUser().getCartItems();
        Order order = new Order();
        Status status = statusRepository.findByName(StatusType.NEW);

        order.getCartItem().addAll(cart);
        order.setTotal(userService.getCartTotal());
        order.setAddress(clientInfoDto.getAddress());
        order.setPaymentType(PaymentType.valueOf(clientInfoDto.getPaymentOptions()));
        order.setDeliveryType(DeliveryType.valueOf(clientInfoDto.getDeliveryOptions()));
        order.setStatus(status);
        order.setCreated(LocalDate.now());
        order.setModified(LocalDate.now());
        order.setUser(userService.getUser());
        orderRepository.save(order);
        userService.getUser().getCartItems().clear();
        return true;

    }
}
