package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.Category;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.SubCategory;

public class ProductShortInfoDto {

    private Long id;

    private String name;

    private Double price;

    private String description;

    private String size;

    private String color;

    private String imageUrl;

    private Category category;

    private SubCategory subCategory;

    public ProductShortInfoDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.size = product.getSize();
        this.color = product.getColor();
        this.imageUrl = product.getImageUrl();
        this.category = product.getCategory();
        this.subCategory = product.getSubCategory();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
