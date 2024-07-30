package bg.softuni.clothing_store.config;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.OrderItem;
import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    private final String CLOUD_NAME = System.getenv("CLOUDINARY_API_NAME");
    private final String API_KEY = System.getenv("CLOUDINARY_KEY");
    private final String API_SECRET = System.getenv("CLOUDINARY_API_SECRET");

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<CartItem, OrderItem> typeMap = modelMapper.createTypeMap(CartItem.class, OrderItem.class);
        typeMap.addMappings(mapper -> mapper.skip(OrderItem::setId));

        return modelMapper;
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);

        return new Cloudinary(config);
    }


}
