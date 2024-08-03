package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProductShortInfoDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private Integer quantity;

    private boolean isInStock;

    private Set<Size> size;

    private Set<Color> color;

    private List<String> images;

//    private String firstImage;
//
//    private String secondImage;
//
//    private String thirdImage;
//
//    private String fourthImage;
//
//    private String fifthImage;

    private Category category;

    private SubCategory subCategory;

    private String rating;



    public ProductShortInfoDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.isInStock = product.isInStock();
        this.description = product.getDescription();
        this.size = product.getSize();
        this.color = product.getColor();
        this.images = product.getImages();
//        this.firstImage = product.getFirstImage();
//        this.secondImage = product.getSecondImage();
//        this.thirdImage = product.getThirdImage();
//        this.fourthImage = product.getFourthImage();
//        this.fifthImage = product.getFifthImage();
        this.category = product.getCategory();
        this.subCategory = product.getSubCategory();
        this.rating = product.getRating();

    }

    //#######################################################

}
