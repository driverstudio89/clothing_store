package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRegisterDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 4, max = 50)
    private String password;

    @NotBlank
    @Size(min = 4, max = 50)
    private String confirmPassword;

    public @NotBlank @Size(min = 3, max = 50) String getUsername() {
        return username;
    }

    public UserRegisterDto setUsername(@NotBlank @Size(min = 3, max = 50) String username) {
        this.username = username;
        return this;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(@NotBlank @Email String email) {
        this.email = email;
        return this;
    }

    public @NotBlank @Size(min = 3, max = 50) String getFirstName() {
        return firstName;
    }

    public UserRegisterDto setFirstName(@NotBlank @Size(min = 3, max = 50) String firstName) {
        this.firstName = firstName;
        return this;
    }

    public @NotBlank @Size(min = 3, max = 50) String getLastName() {
        return lastName;
    }

    public UserRegisterDto setLastName(@NotBlank @Size(min = 3, max = 50) String lastName) {
        this.lastName = lastName;
        return this;
    }

    public @NotBlank @Size(min = 4, max = 50) String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(@NotBlank @Size(min = 4, max = 50) String password) {
        this.password = password;
        return this;
    }

    public @NotBlank @Size(min = 4, max = 50) String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDto setConfirmPassword(@NotBlank @Size(min = 4, max = 50) String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
