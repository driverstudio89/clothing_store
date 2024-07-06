package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.Category;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

}
