package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.model.SubCategory;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {
    public void addProduct(AddProductDto addProductDto, Map<String, MultipartFile> toUpload) throws IOException;

    public Set<ProductShortInfoDto> getLastProducts();

    ProductShortInfoDto getProductDetails(long id);

    void addInitialProduct(String name, String description,
                           BigDecimal price, int quantity, List<String> images,
                           String color, String size, String subCategory, String category);

    void outOfStock(long id);

    Set<ProductShortInfoDto> getProducts(CategoryType categoryType);

    Set<ProductShortInfoDto> getProducts(CategoryType categoryType, SubCategoryType subCategoryType);

    List<SubCategoryType> getSubcategoryMen();

    String getProductImage(long id);

    String getRating(long id);
}
