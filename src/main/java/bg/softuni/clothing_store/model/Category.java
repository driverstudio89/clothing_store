package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.CategoryType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(targetEntity = Product.class, mappedBy = "category")
    private Set<Product> products;

    //##########################################################


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
