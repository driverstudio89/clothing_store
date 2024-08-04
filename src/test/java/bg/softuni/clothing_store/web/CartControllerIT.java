package bg.softuni.clothing_store.web;

import bg.softuni.clothing_store.service.CartItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartControllerIT {

    @Autowired
    private CartItemService cartItemService;


}
