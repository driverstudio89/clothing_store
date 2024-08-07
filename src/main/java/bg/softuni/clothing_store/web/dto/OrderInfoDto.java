package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.OrderItem;
import bg.softuni.clothing_store.model.Status;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.model.enums.DeliveryType;
import bg.softuni.clothing_store.model.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@NoArgsConstructor

public class OrderInfoDto {

    private Long id;

    private List<OrderItem> orderItems;

    private BigDecimal total;

    private Status status;

    private PaymentType paymentType;

    private DeliveryType deliveryType;

    private LocalDateTime created;

    private LocalDateTime modified;

    private User user;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phoneNumber;

    private String country;

    private String city;

    private String zip;

    public Long getId() {
        return id;
    }

    public OrderInfoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderInfoDto setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderInfoDto setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public OrderInfoDto setStatus(Status status) {
        this.status = status;
        return this;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public OrderInfoDto setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public OrderInfoDto setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public OrderInfoDto setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public OrderInfoDto setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

    public User getUser() {
        return user;
    }

    public OrderInfoDto setUser(User user) {
        this.user = user;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public OrderInfoDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public OrderInfoDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public OrderInfoDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderInfoDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderInfoDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public OrderInfoDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public OrderInfoDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public OrderInfoDto setZip(String zip) {
        this.zip = zip;
        return this;
    }
}
