package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.SubCategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "sub_category")
@Getter
@Setter
@NoArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubCategoryType subCategory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(targetEntity = Product.class, mappedBy = "subCategory")
    private Set<Product> products;

    public SubCategory(SubCategoryType subCategoryType, String description) {
        this.subCategory = subCategoryType;
        this.description = description;
    }

}
