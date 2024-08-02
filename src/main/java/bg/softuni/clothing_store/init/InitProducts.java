package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InitProducts  {


    private final ProductRepository productRepository;
    private final ProductService productService;

    public void run(String... args) throws Exception {
        if (productRepository.count() > 0) {
            return;
        }

        productService.addInitialProduct(
                "Black t-shirt",
                "black t-shirt with letters",
                BigDecimal.valueOf(29.99),
                5,
                "https://res.cloudinary.com/dpwynqscy/image/upload/v1720705485/d29f1211-b05c-4eca-bad5-8e46ff2c48d2.avif",
                "https://res.cloudinary.com/dpwynqscy/image/upload/v1720705485/d29f1211-b05c-4eca-bad5-8e46ff2c48d2.avif",
                "BLACK",
                "M",
                "SHIRT",
                "MEN"
                );

        productService.addInitialProduct(
                "Pink t-shirt",
                "Pink t-shirt with blue letters",
                BigDecimal.valueOf(32.5),
                5,
                "https://res.cloudinary.com/dpwynqscy/image/upload/v1720705549/fcd975c6-8e72-4d69-8ac7-7d64de46e146.avif",
                "https://res.cloudinary.com/dpwynqscy/image/upload/v1720705549/fcd975c6-8e72-4d69-8ac7-7d64de46e146.avif",
                "PINK",
                "s",
                "SHIRT",
                "MEN"
        );

        productService.addInitialProduct(
                "Camo t-shirt",
                "Camo t-shirt white base with green and beige pattern",
                BigDecimal.valueOf(35.5),
                5,
                "https://res.cloudinary.com/dpwynqscy/image/upload/v1720705641/dfce503e-b3e0-4d8f-b3d9-d53a5c49e3a3.avif",
                "https://res.cloudinary.com/dpwynqscy/image/upload/v1720705641/dfce503e-b3e0-4d8f-b3d9-d53a5c49e3a3.avif",
                "CAMO",
                "XL",
                "SHIRT",
                "MEN"
        );

        productService.addInitialProduct(
                "Brown t-shirt",
                "Plain brown t-shirt",
                BigDecimal.valueOf(19.99),
                5,
                "https://res.cloudinary.com/dpwynqscy/image/upload/v1720705696/8b433c12-e0f9-44fa-874d-8bb73f67a185.avif",
                "https://res.cloudinary.com/dpwynqscy/image/upload/v1720705696/8b433c12-e0f9-44fa-874d-8bb73f67a185.avif",
                "BROWN",
                "M",
                "SHIRT",
                "MEN"
        );

        productService.addInitialProduct(
                "White t-shirt",
                "White t-shirt with blue letters on pink background",
                BigDecimal.valueOf(25.5),
                10,
                "http://res.cloudinary.com/dpwynqscy/image/upload/v1720705755/5c125ab6-7a7c-4ab7-a569-734c173ce114.avif",
                "http://res.cloudinary.com/dpwynqscy/image/upload/v1720705755/5c125ab6-7a7c-4ab7-a569-734c173ce114.avif",
                "WHITE",
                "S",
                "SHIRT",
                "MEN"
        );


    }
}
