package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
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

    public Category(CategoryType categoryType, String description) {
        this.category = categoryType;
        this.description = description;
    }

    //##########################################################

}
