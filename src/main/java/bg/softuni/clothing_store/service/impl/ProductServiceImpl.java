package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.*;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.service.CartItemService;
import bg.softuni.clothing_store.service.CloudinaryService;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import bg.softuni.clothing_store.web.dto.AddToCartDto;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;
import com.cloudinary.Cloudinary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements bg.softuni.clothing_store.service.ProductService {

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
        ProductShortInfoDto productShortInfoDto = productRepository.findById(id).map(p -> modelMapper.map(p, ProductShortInfoDto.class)).orElse(null);
        return productShortInfoDto;
    }

    @Override
    public void addInitialProduct(String name, String description, double price,
                                  int quantity, String imageUrl, String color,
                                  String size, String subCategory, String category) {

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        product.setCreated(LocalDate.now());
        product.setModified(LocalDate.now());

        product.setQuantity(quantity);
        product.setFirstImage(imageUrl);
        product.getColor().add((colorRepository.findByColorName(ColorName.valueOf(color.toUpperCase()))));
        product.getSize().add(sizeRepository.findBySizeName(SizeName.valueOf(size.toUpperCase())));
        product.setSubCategory(subCategoryRepository.findBySubCategory(SubCategoryType.valueOf(subCategory.toUpperCase())));
        product.setCategory(categoryRepository.findByCategory(CategoryType.valueOf(category.toUpperCase())));
        productRepository.save(product);
    }

}
