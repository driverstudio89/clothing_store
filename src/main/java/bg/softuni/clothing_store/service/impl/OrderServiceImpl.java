package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.OrderItemRepository;
import bg.softuni.clothing_store.data.OrderRepository;
import bg.softuni.clothing_store.data.StatusRepository;
import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.Order;
import bg.softuni.clothing_store.model.OrderItem;
import bg.softuni.clothing_store.model.Status;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.model.enums.StatusType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import bg.softuni.clothing_store.web.dto.OrderInfoDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final StatusRepository statusRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemService cartItemService;



    @Override
    @Transactional
    public boolean createOrder(ClientInfoDto clientInfoDto) {
        userService.addUserData(clientInfoDto);

        Set<OrderItem> cart = userService.getUser().getCartItems()
                .stream().map(c -> {
                            OrderItem orderItem = modelMapper.map(c, OrderItem.class);
                            orderItemRepository.save(orderItem);
                            return orderItem;
                        }
                        ).collect(Collectors.toSet());


        Order order = new Order();
        Status status = statusRepository.findByName(StatusType.NEW);

        order.getOrderItems().addAll(new HashSet<>(cart));
        order.setTotal(userService.getCartTotal());
        order.setAddress(clientInfoDto.getAddress());
        order.setPaymentType(PaymentType.valueOf(clientInfoDto.getPaymentOptions()));
        order.setDeliveryType(DeliveryType.valueOf(clientInfoDto.getDeliveryOptions()));
        order.setStatus(status);
        order.setCreated(LocalDate.now());
        order.setModified(LocalDate.now());
        order.setUser(userService.getUser());
        orderRepository.save(order);
        for (CartItem cartItem : userService.getUser().getCartItems()) {
            cartItemService.removeFromCart(cartItem.getId());
        }
        System.out.println();
        return true;

    }

    @Override
    @Transactional
    public LinkedHashSet<OrderInfoDto> getAllOrders() {
        LinkedHashSet<OrderInfoDto> allOrders = orderRepository.findAllByOrderByCreatedDesc()
                .stream().map(o -> {
                    return modelMapper.map(o, OrderInfoDto.class);
                }).collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println();
        return allOrders;

    }
}
