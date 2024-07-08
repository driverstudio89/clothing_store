package bg.softuni.clothing_store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToMany(targetEntity = Size.class, fetch = FetchType.EAGER)
    private Set<Size> size;

    @ManyToMany(targetEntity = Color.class, fetch = FetchType.EAGER)
    private Set<Color> color;

//    @Column(nullable = false)
//    private int quantity;

    @Column(nullable = false)
    private String firstImage;

    private String secondImage;

    private String thirdImage;

    private String fourthImage;

    private String fifthImage;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private LocalDate modified;

    @ManyToOne(targetEntity = Category.class)
    private Category category;

    @ManyToOne(targetEntity = SubCategory.class)
    private SubCategory subCategory;

    public Product() {
        this.size = new HashSet<>();
        this.color = new HashSet<>();
    }

    //#######################################################

}
