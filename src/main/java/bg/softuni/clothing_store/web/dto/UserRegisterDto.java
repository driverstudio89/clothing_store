package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {

    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 5, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 5, max = 50)
    private String password;

    @NotBlank
    @Size(min = 5, max = 50)
    private String confirmPassword;

    public @NotBlank @Size(min = 5, max = 50) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 5, max = 50) String username) {
        this.username = username;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 5, max = 50) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank @Size(min = 5, max = 50) String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank @Size(min = 5, max = 50) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank @Size(min = 5, max = 50) String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank @Size(min = 5, max = 50) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 5, max = 50) String password) {
        this.password = password;
    }

    public @NotBlank @Size(min = 5, max = 50) String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank @Size(min = 5, max = 50) String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
