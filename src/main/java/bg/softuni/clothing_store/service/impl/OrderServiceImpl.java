package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.*;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import bg.softuni.clothing_store.model.enums.StatusType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.OrderService;
import bg.softuni.clothing_store.service.UserService;
import bg.softuni.clothing_store.service.exception.ObjectNotFoundException;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final RestClient ordersRestClient;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final StatusRepository statusRepository;
    private final CartItemService cartItemService;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public boolean createOrder(ClientInfoDto clientInfoDto) {

        OrderRestDto orderRestDto = createOrderRestDto(clientInfoDto);

        System.out.println();

        LOGGER.info("Creating new order...");
        RestClient.ResponseSpec retrieve = ordersRestClient
                .post()
                .uri("/administration/orders/add-order")
                .body(orderRestDto)
                .retrieve();

        removeItemsFromUserCart();
        System.out.println();
        return true;

    }

    private void removeItemsFromUserCart() {
        for (CartItem cartItem : userService.getUser().getCartItems()) {
            cartItemService.removeFromCart(cartItem.getId());
        }
    }

    private OrderRestDto createOrderRestDto(ClientInfoDto clientInfoDto) {
        OrderRestDto orderRestDto = new OrderRestDto();
        modelMapper.map(clientInfoDto, orderRestDto);
        User user = userService.getUser();

        List<OrderItemDto> cart = user.getCartItems()
                .stream().map(c -> {
                            OrderItemDto orderItemDto = modelMapper.map(c, OrderItemDto.class);
                            orderItemDto.setProductId(c.getProduct().getId());
                            orderItemDto.setSizes(c.getSizes().getSizeName());
                            orderItemDto.setColors(c.getColors().getColorName());
                            return orderItemDto;
                        }
                ).toList();

        orderRestDto.setOrderItemsRest(cart);
        orderRestDto.setUser(user.getId());
        orderRestDto.setTotal(userService.getCartTotal());
        orderRestDto.setPaymentType(PaymentType.valueOf(clientInfoDto.getPaymentOptions()));
        orderRestDto.setDeliveryType(DeliveryType.valueOf(clientInfoDto.getDeliveryOptions()));

        user.setAddress(clientInfoDto.getAddress());
        user.setPhoneNumber(clientInfoDto.getPhoneNumber());
        user.setCountry(clientInfoDto.getCountry());
        user.setCity(clientInfoDto.getCity());
        user.setZip(clientInfoDto.getZip());
        return orderRestDto;
    }


    @Override
    @Transactional
    public OrderInfoDto getOrderDetails(long id) {

        OrderInfoRestDto orderInfoRestDtos = ordersRestClient
                .get()
                .uri("/administration/orders/order-" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        if (orderInfoRestDtos == null) {
            throw new ObjectNotFoundException("Order not found!", id);
        }
        OrderInfoDto order = mapRestDtoToOrderInfoDto(orderInfoRestDtos);
        System.out.println();
        return order;
    }

    @Override
    @Transactional
    public void changeStatus(long id, StatusType statusType) {
        ChangeStatusDto changeStatusDto = new ChangeStatusDto();
        changeStatusDto.setStatus(statusType);

        ordersRestClient
                .post()
                .uri("/administration/orders/" + id)
                .body(changeStatusDto)
                .retrieve();

    }

    @Override
    @Transactional
    public List<OrderInfoDto> getAllOrders() {
        LOGGER.info("Get all orders...");

        List<OrderInfoRestDto> orderInfoRestDtos = ordersRestClient
                .get()
                .uri("/administration/orders/all-orders")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        List<OrderInfoDto> orders = new ArrayList<>();

        for (OrderInfoRestDto order : orderInfoRestDtos) {
            orders.add(mapRestDtoToOrderInfoDto(order));
        }

        System.out.println();
        return orders;

    }

    @Override
    @Transactional
    public List<OrderInfoDto> getAllOrders(LocalDate created) {

        List<OrderInfoRestDto> orderInfoRestDtos = ordersRestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/administration/orders/all-orders")
                                .queryParam("created", created)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });


        List<OrderInfoDto> orders = new ArrayList<>();

        for (OrderInfoRestDto order : orderInfoRestDtos) {
            orders.add(mapRestDtoToOrderInfoDto(order));
        }

        System.out.println();
        return orders;
    }

    @Override
    @Transactional
    public List<OrderInfoDto> getAllOrders(StatusType statusType) {
        List<OrderInfoRestDto> orderInfoRestDtos = ordersRestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/administration/orders/all-orders")
                                .queryParam("statusType", statusType)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });


        List<OrderInfoDto> orders = new ArrayList<>();

        for (OrderInfoRestDto order : orderInfoRestDtos) {
            orders.add(mapRestDtoToOrderInfoDto(order));
        }

        System.out.println();
        return orders;
    }

    @Override
    @Transactional
    public List<OrderInfoDto> getAllOrders(LocalDate created, StatusType statusType) {
        List<OrderInfoRestDto> orderInfoRestDtos = ordersRestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/administration/orders/all-orders")
                                .queryParam("created", created)
                                .queryParam("statusType", statusType)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });


        List<OrderInfoDto> orders = new ArrayList<>();

        for (OrderInfoRestDto order : orderInfoRestDtos) {
            orders.add(mapRestDtoToOrderInfoDto(order));
        }

        System.out.println();
        return orders;
    }

    @Override
    @Transactional
    public List<OrderInfoDto> allUserOrders() {

        List<OrderInfoRestDto> orderInfoRestDtos = ordersRestClient
                .get()
                .uri(uriBuilder ->
                    uriBuilder
                            .path("/users/orders")
                            .queryParam("userId", userService.getUser().getId())
                            .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        List<OrderInfoDto> orders = new ArrayList<>();

        for (OrderInfoRestDto order : orderInfoRestDtos) {
            orders.add(mapRestDtoToOrderInfoDto(order));
        }

        System.out.println();
        return orders;


    }

    @Override
    public void deleteOrderFromUser(long id) {
        ordersRestClient
                .delete()
                .uri("/users/orders/delete/" + id)
                .retrieve();
    }

    private OrderInfoDto mapRestDtoToOrderInfoDto(OrderInfoRestDto orderInfoRestDtos) {

        OrderInfoDto orderInfoDto = new OrderInfoDto();

        User user = userRepository.findById(orderInfoRestDtos.getUser()).get();

        orderInfoDto.setUser(user);

        orderInfoDto.setId(orderInfoRestDtos.getId());
        orderInfoDto.setTotal(orderInfoRestDtos.getTotal());
        orderInfoDto.setPaymentType(orderInfoRestDtos.getPaymentType());
        orderInfoDto.setDeliveryType(orderInfoRestDtos.getDeliveryType());
        orderInfoDto.setCreated(orderInfoRestDtos.getCreated());
        orderInfoDto.setModified(orderInfoRestDtos.getModified());


        List<OrderItemRestDto> orderItemsRest = orderInfoRestDtos.getOrderItemsRest();
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRestDto orderItemRest : orderItemsRest) {
            OrderItem orderItem = new OrderItem();
            Product product = productRepository.findById(orderItemRest.getProductId()).get();
            orderItem.setProduct(product);
            orderItem.setId(orderItemRest.getId());
            orderItem.setQuantity(orderItemRest.getQuantity());
            Size size = sizeRepository.findBySizeName(orderItemRest.getSizes());
            orderItem.setSizes(size);
            Color color = colorRepository.findByColorName(orderItemRest.getColors());
            orderItem.setColors(color);
            orderItem.setUser(user);
            if (user.getReviewedProducts().contains(product)) {
                orderItem.setReviewed(true);
            }
            orderItems.add(orderItem);

        }

        orderInfoDto.setStatus(statusRepository.findByName(orderInfoRestDtos.getStatus()));
        orderInfoDto.setUser(user);
        orderInfoDto.setOrderItems(orderItems);

        return orderInfoDto;

    }


}
