package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.CategoryRepository;
import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.SubCategoryRepository;
import bg.softuni.clothing_store.model.Category;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.SubCategory;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements bg.softuni.clothing_store.service.ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    @Transactional
    public void addProduct(AddProductDto addProductDto) {
        Product product = modelMapper.map(addProductDto, Product.class);
        product.setCreated(LocalDate.now());
        product.setModified(LocalDate.now());

        Category byCategory = categoryRepository.findByCategory(CategoryType.valueOf(addProductDto.getCategory().toUpperCase()));
        SubCategory bySubCategory = subCategoryRepository.findBySubCategory(SubCategoryType.valueOf(addProductDto.getSubCategory().toUpperCase()));

        product.setCategory(byCategory);
        product.setSubCategory(bySubCategory);

        System.out.println();

        productRepository.saveAndFlush(product);


    }

    @Override
    public Set<ProductShortInfoDto> getLastProducts() {
        return productRepository.findTop12OrderByCreatedAfter(LocalDate.now().minusWeeks(1))
                .stream().map(ProductShortInfoDto::new).collect(Collectors.toSet());
    }
}
