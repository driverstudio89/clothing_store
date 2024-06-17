package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.SubCategoryType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "sub_category")
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

    public SubCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubCategoryType getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategoryType subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
