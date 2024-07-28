package bg.softuni.clothing_store.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "orders.api")
@Getter
@Setter
public class OrderApiConfig {
    private String baseUrl;
}
