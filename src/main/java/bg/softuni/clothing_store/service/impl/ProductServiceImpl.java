package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.CartItemRepository;
import bg.softuni.clothing_store.data.CategoryRepository;
import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.SubCategoryRepository;
import bg.softuni.clothing_store.model.Category;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.SubCategory;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.CloudinaryService;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;
import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements bg.softuni.clothing_store.service.ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CloudinaryService cloudinaryService;
    private final CartItemService cartItemService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, Cloudinary cloudinary, CloudinaryService cloudinaryService, CartItemRepository cartItemRepository, CartItemService cartItemService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.cloudinaryService = cloudinaryService;
        this.cartItemService = cartItemService;
    }

    @Override
    @Transactional
    public void addProduct(AddProductDto addProductDto, Map<String, MultipartFile> toUpload) throws IOException {
        Product product = modelMapper.map(addProductDto, Product.class);
        product.setCreated(LocalDate.now());
        product.setModified(LocalDate.now());

        Category byCategory = categoryRepository.findByCategory(CategoryType.valueOf(addProductDto.getCategory().toUpperCase()));
        SubCategory bySubCategory = subCategoryRepository.findBySubCategory(SubCategoryType.valueOf(addProductDto.getSubCategory().toUpperCase()));


//        for (Map.Entry<String, MultipartFile> stringMultipartFileEntry : toUpload.entrySet()) {
//            String imageUrl = cloudinaryService.uploadFile(stringMultipartFileEntry.getValue());
//        }

        System.out.println();
        if (!toUpload.get("firstImage").isEmpty()) {
            String firstImage = cloudinaryService.uploadFile(toUpload.get("firstImage"));
            product.setFirstImage(firstImage);
        }

        if (!toUpload.get("secondImage").isEmpty()) {
            String secondImage = cloudinaryService.uploadFile(toUpload.get("secondImage"));
            product.setSecondImage(secondImage);
        }

        if (!toUpload.get("thirdImage").isEmpty()) {
            String thirdImage = cloudinaryService.uploadFile(toUpload.get("thirdImage"));
            product.setThirdImage(thirdImage);
        }

        if (!toUpload.get("fourthImage").isEmpty()) {
            String fourthImage = cloudinaryService.uploadFile(toUpload.get("fourthImage"));
            product.setFourthImage(fourthImage);
        }

        if (!toUpload.get("fifthImage").isEmpty()) {
            String fifthImage = cloudinaryService.uploadFile(toUpload.get("fifthImage"));
            product.setFifthImage(fifthImage);
        }


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

    @Override
    public ProductShortInfoDto getProductDetails(long id) {
        return productRepository.findById(id).map(p -> modelMapper.map(p, ProductShortInfoDto.class)).orElse(null);
    }

}
