package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.OrderItemRepository;
import bg.softuni.clothing_store.data.OrderRepository;
import bg.softuni.clothing_store.data.StatusRepository;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.model.enums.StatusType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.ClientInfoDto;
import bg.softuni.clothing_store.web.dto.OrderInfoDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    private final UserHelperService userHelperService;
    private final UserRepository userRepository;


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
        User user = userHelperService.getUser();

        order.getOrderItems().addAll(new HashSet<>(cart));
        order.setTotal(userService.getCartTotal());
        order.setAddress(clientInfoDto.getAddress());
        order.setPaymentType(PaymentType.valueOf(clientInfoDto.getPaymentOptions()));
        order.setDeliveryType(DeliveryType.valueOf(clientInfoDto.getDeliveryOptions()));
        order.setStatus(status);
        order.setCreated(LocalDateTime.now());
        order.setModified(LocalDateTime.now());
        order.setUser(userService.getUser());
        orderRepository.save(order);
//        user.getOrders().add(order);
//        userRepository.save(user);
        for (CartItem cartItem : userService.getUser().getCartItems()) {
            cartItemService.removeFromCart(cartItem.getId());
        }
        System.out.println();
        return true;

    }

    @Override
    @Transactional
    public LinkedHashSet<OrderInfoDto> getAllOrders() {
        LinkedHashSet<OrderInfoDto> allOrders = mapOrdersToDto(orderRepository.findAllByOrderByCreatedDesc());
        System.out.println();
        return allOrders;

    }

    @Override
    @Transactional
    public OrderInfoDto getOrderDetails(long id) {
        return modelMapper.map(orderRepository.findById(id), OrderInfoDto.class);
    }

    @Override
    @Transactional
    public BigDecimal getOrderTotal(long id) {
        BigDecimal total = new BigDecimal(0);
        for (OrderItem orderItem : orderRepository.findById(id).get().getOrderItems()) {
            int quantity = orderItem.getQuantity();
            BigDecimal price = orderItem.getProduct().getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
        }
        System.out.println();
        return total;
    }

    @Override
    @Transactional
    public void changeStatus(long id, StatusType statusType) {
        orderRepository.findById(id).get().setStatus(statusRepository.findByName(statusType));
    }

    @Override
    @Transactional
    public LinkedHashSet<OrderInfoDto> getAllOrders(LocalDate created) {
        return mapOrdersToDto(orderRepository.findAllByCreated(created));
    }

    @Override
    @Transactional
    public LinkedHashSet<OrderInfoDto> getAllOrders(StatusType statusType) {
        Status status = statusRepository.findByName(statusType);
        List<Order> orders = orderRepository.findAllByStatus(status);
        return mapOrdersToDto(orders);
    }

    @Override
    @Transactional
    public LinkedHashSet<OrderInfoDto> getAllOrders(LocalDate created, StatusType statusType) {
        Status status = statusRepository.findByName(statusType);
        List<Order> orders = orderRepository.findAllByStatusAndCreated(status, created);
        return mapOrdersToDto(orders);
    }

    @Override
    @Transactional
    public List<OrderInfoDto> allUserOrders() {
        User user = userHelperService.getUser();
        List<Order> orders = orderRepository.findAllByUserIdOrderByCreatedDesc(user.getId());
        return orders.stream().map(o -> {
            return modelMapper.map(o, OrderInfoDto.class);
        }).toList();
    }

    private LinkedHashSet<OrderInfoDto> mapOrdersToDto(List<Order> orders) {
        return orders.stream().map(o -> {
            return modelMapper.map(o, OrderInfoDto.class);
        }).collect(Collectors.toCollection(LinkedHashSet::new));
    }


}
