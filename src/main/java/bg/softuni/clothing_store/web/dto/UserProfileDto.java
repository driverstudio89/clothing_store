package bg.softuni.clothing_store.web.dto;


import bg.softuni.clothing_store.model.Order;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDto {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String city;

    private String country;

    private String zip;

}