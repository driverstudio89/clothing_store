package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.SubCategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "sub_category")
@NoArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubCategoryType subCategory;

    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
    private String description;

    @OneToMany(targetEntity = Product.class, mappedBy = "subCategory")
    private Set<Product> products;

    public SubCategory(SubCategoryType subCategoryType, String description) {
        this.subCategory = subCategoryType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public SubCategory setId(Long id) {
        this.id = id;
        return this;
    }

    public SubCategoryType getSubCategory() {
        return subCategory;
    }

    public SubCategory setSubCategory(SubCategoryType subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SubCategory setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public SubCategory setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }
}
