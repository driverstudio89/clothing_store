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

    private String firstImage;

    private String secondImage;

    private String thirdImage;

    private String fourthImage;

    private String fifthImage;

    private Category category;

    private SubCategory subCategory;

    public ProductShortInfoDto() {
    }

    public ProductShortInfoDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.size = product.getSize();
        this.color = product.getColor();
        this.firstImage = product.getFirstImage();
        this.secondImage = product.getSecondImage();
        this.thirdImage = product.getThirdImage();
        this.fourthImage = product.getFourthImage();
        this.fifthImage = product.getFifthImage();
        this.category = product.getCategory();
        this.subCategory = product.getSubCategory();

    }

    //#######################################################


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

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public void setSecondImage(String secondImage) {
        this.secondImage = secondImage;
    }

    public String getThirdImage() {
        return thirdImage;
    }

    public void setThirdImage(String thirdImage) {
        this.thirdImage = thirdImage;
    }

    public String getFourthImage() {
        return fourthImage;
    }

    public void setFourthImage(String fourthImage) {
        this.fourthImage = fourthImage;
    }

    public String getFifthImage() {
        return fifthImage;
    }

    public void setFifthImage(String fifthImage) {
        this.fifthImage = fifthImage;
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
