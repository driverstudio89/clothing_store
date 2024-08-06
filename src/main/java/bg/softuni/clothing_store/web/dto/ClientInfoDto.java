package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class ClientInfoDto {

    private long id;

    @NotNull(message = "{checkout_error_first_name}")
    @Size(min = 3, max = 50, message = "{checkout_error_first_name}")
    private String firstName;

    @NotNull(message = "{checkout_error_last_name}")
    @Size(min = 3, max = 50, message = "{checkout_error_last_name}")
    private String lastName;

    @NotBlank(message = "{checkout_error_email}")
    @Email(message = "{checkout_error_email}")
    private String email;

    @NotNull(message = "{checkout_error_phone_number}")
    @Size(min = 8, max = 12, message = "{checkout_error_phone_number}")
    private String phoneNumber;

    @NotBlank(message = "{checkout_error_phone_address}")
    private String address;

    @NotBlank(message = "{checkout_error_phone_country}")
    private String country;

    @NotBlank(message = "{checkout_error_city}")
    private String city;

    @NotBlank(message = "{checkout_error_zip}")
    private String zip;

    @NotBlank(message = "{checkout_error_delivery_options}")
    private String deliveryOptions;

    @NotBlank(message = "{checkout_error_payment_options}")
    private String paymentOptions;


    //##############################

    public long getId() {
        return id;
    }

    public ClientInfoDto setId(long id) {
        this.id = id;
        return this;
    }

    public @NotNull(message = "{checkout_error_first_name}") @Size(min = 3, max = 50, message = "{checkout_error_first_name}") String getFirstName() {
        return firstName;
    }

    public ClientInfoDto setFirstName(@NotNull(message = "{checkout_error_first_name}") @Size(min = 3, max = 50, message = "{checkout_error_first_name}") String firstName) {
        this.firstName = firstName;
        return this;
    }

    public @NotNull(message = "{checkout_error_last_name}") @Size(min = 3, max = 50, message = "{checkout_error_last_name}") String getLastName() {
        return lastName;
    }

    public ClientInfoDto setLastName(@NotNull(message = "{checkout_error_last_name}") @Size(min = 3, max = 50, message = "{checkout_error_last_name}") String lastName) {
        this.lastName = lastName;
        return this;
    }

    public @NotBlank(message = "{checkout_error_email}") @Email(message = "{checkout_error_email}") String getEmail() {
        return email;
    }

    public ClientInfoDto setEmail(@NotBlank(message = "{checkout_error_email}") @Email(message = "{checkout_error_email}") String email) {
        this.email = email;
        return this;
    }

    public @NotNull(message = "{checkout_error_phone_number}") @Size(min = 8, max = 12, message = "{checkout_error_phone_number}") String getPhoneNumber() {
        return phoneNumber;
    }

    public ClientInfoDto setPhoneNumber(@NotNull(message = "{checkout_error_phone_number}") @Size(min = 8, max = 12, message = "{checkout_error_phone_number}") String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public @NotBlank(message = "{checkout_error_phone_address}") String getAddress() {
        return address;
    }

    public ClientInfoDto setAddress(@NotBlank(message = "{checkout_error_phone_address}") String address) {
        this.address = address;
        return this;
    }

    public @NotBlank(message = "{checkout_error_phone_country}") String getCountry() {
        return country;
    }

    public ClientInfoDto setCountry(@NotBlank(message = "{checkout_error_phone_country}") String country) {
        this.country = country;
        return this;
    }

    public @NotBlank(message = "{checkout_error_city}") String getCity() {
        return city;
    }

    public ClientInfoDto setCity(@NotBlank(message = "{checkout_error_city}") String city) {
        this.city = city;
        return this;
    }

    public @NotBlank(message = "{checkout_error_zip}") String getZip() {
        return zip;
    }

    public ClientInfoDto setZip(@NotBlank(message = "{checkout_error_zip}") String zip) {
        this.zip = zip;
        return this;
    }

    public @NotBlank(message = "{checkout_error_delivery_options}") String getDeliveryOptions() {
        return deliveryOptions;
    }

    public ClientInfoDto setDeliveryOptions(@NotBlank(message = "{checkout_error_delivery_options}") String deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
        return this;
    }

    public @NotBlank(message = "{checkout_error_payment_options}") String getPaymentOptions() {
        return paymentOptions;
    }

    public ClientInfoDto setPaymentOptions(@NotBlank(message = "{checkout_error_payment_options}") String paymentOptions) {
        this.paymentOptions = paymentOptions;
        return this;
    }
}
