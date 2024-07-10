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

    @NotNull(message = "First name must be between 3 and 50 characters!")
    @Size(min = 3, max = 50, message = "FirstName must be between 3 and 50 characters!")
    private String firstName;

    @NotNull(message = "Last name must be between 3 and 50 characters!")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters!")
    private String lastName;

    @NotBlank(message = "Please enter valid email!")
    @Email(message = "Please enter valid email!")
    private String email;

    @NotBlank(message = "Phone number must between 8 and 12 digits!")
    @Size(min = 8, max = 12, message = "Phone number must between 8 and 12 digits!")
    private String phoneNumber;

    @NotBlank(message = "Need to enter address")
    private String address;

    @NotBlank(message = "Need to enter country")
    private String country;

    @NotBlank(message = "Need to enter city")
    private String city;

    @NotBlank(message = "Need to enter zip")
    private String zip;
}
