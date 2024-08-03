package bg.softuni.clothing_store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToMany(targetEntity = Size.class, fetch = FetchType.EAGER)
    private Set<Size> size;

    @ManyToMany(targetEntity = Color.class, fetch = FetchType.EAGER)
    private Set<Color> color;

    @Column(nullable = false)
    private int quantity;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private LocalDate modified;

    @ManyToOne(targetEntity = Category.class)
    private Category category;

    @ManyToOne(targetEntity = SubCategory.class)
    private SubCategory subCategory;

    @Column(nullable = false)
    private boolean inStock = false;

    @Column
    private String rating;

    @Column
    private long stars;

    @Column
    private long voted;

    public Product() {
        this.size = new HashSet<>();
        this.color = new HashSet<>();
        this.images = new ArrayList<>();
        this.rating = "0";
        this.stars = 0;
        this.voted = 0;
    }

    //#######################################################

}
