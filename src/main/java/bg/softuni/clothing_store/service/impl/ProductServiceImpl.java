package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.web.dto.addProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductServiceImpl implements bg.softuni.clothing_store.service.ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addProduct(addProductDto addProductDto) {
        Product product = modelMapper.map(addProductDto, Product.class);
        product.setCreated(LocalDate.now());
        product.setModified(LocalDate.now());

        System.out.println();

        productRepository.saveAndFlush(product);


    }
}
