package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
}
