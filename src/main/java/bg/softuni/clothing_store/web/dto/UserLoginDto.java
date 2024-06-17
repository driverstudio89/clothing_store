package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 4, max = 50)
    private String password;

    public @NotBlank @Size(min = 3, max = 50) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 50) String username) {
        this.username = username;
    }

    public @NotBlank @Size(min = 4, max = 50) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 4, max = 50) String password) {
        this.password = password;
    }
}
