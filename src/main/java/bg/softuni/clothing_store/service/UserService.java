package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.web.dto.UserLoginDto;
import bg.softuni.clothing_store.web.dto.UserRegisterDto;

public interface UserService {

    void register(UserRegisterDto userRegisterDto);

    void login(UserLoginDto loginData);

    void logout();

}
