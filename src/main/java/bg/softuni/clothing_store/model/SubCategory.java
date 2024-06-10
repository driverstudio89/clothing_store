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

    @ManyToOne()
    private Category category;

    @OneToMany(targetEntity = Product.class, mappedBy = "subCategory")
    private Set<Product> products;

}
