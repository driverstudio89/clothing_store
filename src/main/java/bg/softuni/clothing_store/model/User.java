package bg.softuni.clothing_store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(targetEntity = CartItem.class, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<CartItem> cartItems;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String zip;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    private List<Role> roles;

//    @OneToMany(targetEntity = Order.class, mappedBy = "user")
//    private List<Order> orders;

    @ManyToMany
    private Set<Product> favorites;

    @ManyToMany
    private Set<Product> reviewedProducts;

    public User() {
        this.cartItems = new HashSet<>();
//        this.orders = new ArrayList<>();
        this.roles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public User setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public User setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public User setOrders(List<Order> orders) {
//        this.orders = orders;
//        return this;
//    }

    public Set<Product> getFavorites() {
        return favorites;
    }

    public User setFavorites(Set<Product> favorites) {
        this.favorites = favorites;
        return this;
    }

    public Set<Product> getReviewedProducts() {
        return reviewedProducts;
    }

    public User setReviewedProducts(Set<Product> reviewedProducts) {
        this.reviewedProducts = reviewedProducts;
        return this;
    }

    //#################################################################################


}
