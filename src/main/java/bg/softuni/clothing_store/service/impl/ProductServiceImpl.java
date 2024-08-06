package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.*;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.*;
import bg.softuni.clothing_store.service.*;
import bg.softuni.clothing_store.service.exception.ObjectNotFoundException;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CloudinaryService cloudinaryService;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    @Override
    @Transactional
    public void addProduct(AddProductDto addProductDto, Map<String, MultipartFile> toUpload) throws IOException {
        System.out.println();

        Product product = new Product();

        product.setName(addProductDto.getName());
        product.setDescription(addProductDto.getDescription());
        product.setPrice(addProductDto.getPrice());
        product.setQuantity((addProductDto.getQuantity()));
        if (addProductDto.getQuantity() > 0) {
            product.setInStock(true);
        }

        product.setCreated(LocalDate.now());
        product.setModified(LocalDate.now());

        Category byCategory = categoryRepository.findByCategory(CategoryType.valueOf(addProductDto.getCategory().toUpperCase()));
        SubCategory bySubCategory = subCategoryRepository.findBySubCategory(SubCategoryType.valueOf(addProductDto.getSubCategory().toUpperCase()));
        for (String sizeName : addProductDto.getSize()) {
            Size size = sizeRepository.findBySizeName((SizeName.valueOf(sizeName)));
            product.getSize().add(size);
            sizeRepository.save(size);
        }
        for (String colorName : addProductDto.getColor()) {
            Color color = colorRepository.findByColorName(ColorName.valueOf(colorName));
            product.getColor().add(color);
            colorRepository.save(color);
        }

        System.out.println();
        if (!toUpload.get("firstImage").isEmpty()) {
            String firstImage = cloudinaryService.uploadFile(toUpload.get("firstImage"));
            product.getImages().add(firstImage);
        }

        if (!toUpload.get("secondImage").isEmpty()) {
            String secondImage = cloudinaryService.uploadFile(toUpload.get("secondImage"));
            product.getImages().add(secondImage);
        }

        if (!toUpload.get("thirdImage").isEmpty()) {
            String thirdImage = cloudinaryService.uploadFile(toUpload.get("thirdImage"));
            product.getImages().add(thirdImage);
        }

        if (!toUpload.get("fourthImage").isEmpty()) {
            String fourthImage = cloudinaryService.uploadFile(toUpload.get("fourthImage"));
            product.getImages().add(fourthImage);
        }

        if (!toUpload.get("fifthImage").isEmpty()) {
            String fifthImage = cloudinaryService.uploadFile(toUpload.get("fifthImage"));
            product.getImages().add(fifthImage);
        }

        product.setCategory(byCategory);
        product.setSubCategory(bySubCategory);

        System.out.println();

        productRepository.saveAndFlush(product);
    }

    @Override
    public Set<ProductShortInfoDto> getLastProducts() {
        Set<Product> products = productRepository.findTop12OrderByCreatedAfter(LocalDate.now().minusWeeks(1));
        return mapProductsToDto(products);
    }


    private static Set<ProductShortInfoDto> mapProductsToDto(Set<Product> products) {
        return products.stream().filter(Product::isInStock)
                .map(ProductShortInfoDto::new).collect(Collectors.toSet());
    }

    @Override
    public ProductShortInfoDto getProductDetails(long id) {
        Optional<ProductShortInfoDto> productShortInfoDto = productRepository.findById(id).map(p -> modelMapper.map(p, ProductShortInfoDto.class));
        if (productShortInfoDto.isEmpty()) {
            throw new ObjectNotFoundException("product not found", id);
        }
        return productShortInfoDto.get();
    }

    @Override
    public void addInitialProduct(String name, String description, BigDecimal price, int quantity,
                                  List<String> images,
                                  String color, String size, String subCategory, String category) {

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        product.setCreated(LocalDate.now());
        product.setModified(LocalDate.now());

        product.setQuantity(quantity);
        if (quantity > 0) {
            product.setInStock(true);
        }
        product.getImages().addAll(images);
        product.getColor().add((colorRepository.findByColorName(ColorName.valueOf(color.toUpperCase()))));
        product.getSize().add(sizeRepository.findBySizeName(SizeName.valueOf(size.toUpperCase())));
        product.setSubCategory(subCategoryRepository.findBySubCategory(SubCategoryType.valueOf(subCategory.toUpperCase())));
        product.setCategory(categoryRepository.findByCategory(CategoryType.valueOf(category.toUpperCase())));
        productRepository.save(product);
    }

    @Override
    public void outOfStock(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ObjectNotFoundException("product not found", id);
        }
            Product product = optionalProduct.get();
            product.setInStock(false);
            product.setPrice(BigDecimal.valueOf(0));
            productRepository.save(product);
            System.out.println();
    }

    @Override
    public Set<ProductShortInfoDto> getProducts(CategoryType categoryType) {
        Category category = categoryRepository.findByCategory(categoryType);
        Set<Product> allByCategory = productRepository.findAllByCategory(category);
        return mapProductsToDto(allByCategory);
    }

    @Override
    @Transactional
    public Set<ProductShortInfoDto> getProducts(CategoryType categoryType, SubCategoryType subCategoryType) {
        Category category = categoryRepository.findByCategory(categoryType);
        SubCategory subCategory = subCategoryRepository.findBySubCategory(subCategoryType);

        Set<Product> allByCategory = productRepository.findAllByCategoryAndSubCategory(category, subCategory);
        return mapProductsToDto(allByCategory);
    }

    @Override
    public List<SubCategoryType> getSubcategoryMen() {
        List<SubCategoryType> subCategoryType = new ArrayList<>();
        for (SubCategoryType s : SubCategoryType.values()) {
            if (!s.equals(SubCategoryType.DRESS) && !s.equals(SubCategoryType.TOP) && !s.equals(SubCategoryType.SKIRT) && !s.equals(SubCategoryType.BLOUSE)) {
                subCategoryType.add(s);
            }
        }
        return subCategoryType;
    }

    @Override
    public String getProductImage(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ObjectNotFoundException("product not found", id);
        }
         return optionalProduct.get().getImages().getFirst();
    }

    @Override
    public String getRating(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ObjectNotFoundException("product not found", id);
        }
        return optionalProduct.get().getRating();
    }


}
