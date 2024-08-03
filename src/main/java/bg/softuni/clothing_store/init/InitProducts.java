package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitProducts {


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
                List.of("https://res.cloudinary.com/dpwynqscy/image/upload/v1720705485/d29f1211-b05c-4eca-bad5-8e46ff2c48d2.avif"),
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
                List.of("https://res.cloudinary.com/dpwynqscy/image/upload/v1720705549/fcd975c6-8e72-4d69-8ac7-7d64de46e146.avif"),
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
                List.of("https://res.cloudinary.com/dpwynqscy/image/upload/v1720705641/dfce503e-b3e0-4d8f-b3d9-d53a5c49e3a3.avif"),
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
                List.of("https://res.cloudinary.com/dpwynqscy/image/upload/v1720705696/8b433c12-e0f9-44fa-874d-8bb73f67a185.avif"),
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
                List.of("http://res.cloudinary.com/dpwynqscy/image/upload/v1720705755/5c125ab6-7a7c-4ab7-a569-734c173ce114.avif"),
                "WHITE",
                "S",
                "SHIRT",
                "MEN"
        );

        productService.addInitialProduct(
                "Short jeans",
                "Short blue jeans, made out of cotton with a few wear marks",
                BigDecimal.valueOf(49.90),
                5,
                List.of("http://res.cloudinary.com/dpwynqscy/image/upload/v1722663555/a502421e-237b-478b-aebf-e4a41adb129b.avif",
                        "http://res.cloudinary.com/dpwynqscy/image/upload/v1722663557/a6ac14d5-8193-4d60-9ec4-6c505291de60.webp"),
                "BLUE",
                "M",
                "JEANS",
                "MEN"
        );

        productService.addInitialProduct(
                "Pink dress",
                "Simple pink dress, material viscose",
                BigDecimal.valueOf(59.50),
                5,
                List.of("http://res.cloudinary.com/dpwynqscy/image/upload/v1722664504/07bf7c0e-ee11-4700-8bc8-34d04e21e0ed.avif",
                        "http://res.cloudinary.com/dpwynqscy/image/upload/v1722664505/3dc3e48d-2fa4-47a3-80b4-9ceb29711a23.avif",
                        "http://res.cloudinary.com/dpwynqscy/image/upload/v1722664503/754bdf28-8f7f-4647-9eba-6191e632d7dc.avif"),
                "PINK",
                "S",
                "DRESS",
                "WOMEN"
        );

        productService.addInitialProduct(
                "Jeans jacket",
                "Ligth colored jeans jacket, made out of recycled materials, it have soft padding and it is suitable for colder weather",
                BigDecimal.valueOf(149.50),
                5,
                List.of("http://res.cloudinary.com/dpwynqscy/image/upload/v1722667364/971ffb4e-759a-4024-a7d3-0f7a10106ac9.avif",
                        "http://res.cloudinary.com/dpwynqscy/image/upload/v1722665251/a52441ad-ee93-4674-8a9d-4348bb8a516d.avif",
                        "http://res.cloudinary.com/dpwynqscy/image/upload/v1722665252/973dd75c-532f-4c75-8ecb-43fb581847e3.avif"),
                "BLUE",
                "L",
                "JACKET",
                "MEN"
        );


    }
}
