package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.web.dto.AddProductDto;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;

import java.util.Set;

public interface ProductService {
    public void addProduct(AddProductDto addProductDto, String imageUrl);

    public Set<ProductShortInfoDto> getLastProducts();
}
