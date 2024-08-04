package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
    private String description;

    @OneToMany(targetEntity = Product.class, mappedBy = "category")
    private Set<Product> products;

    public Category(CategoryType categoryType, String description) {
        this.category = categoryType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Category setId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryType getCategory() {
        return category;
    }

    public Category setCategory(CategoryType category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Category setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }

    //##########################################################

}
