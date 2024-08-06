package bg.softuni.clothing_store.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
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


    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Size> getSize() {
        return size;
    }

    public Product setSize(Set<Size> size) {
        this.size = size;
        return this;
    }

    public Set<Color> getColor() {
        return color;
    }

    public Product setColor(Set<Color> color) {
        this.color = color;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public List<String> getImages() {
        return images;
    }

    public Product setImages(List<String> images) {
        this.images = images;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Product setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public LocalDate getModified() {
        return modified;
    }

    public Product setModified(LocalDate modified) {
        this.modified = modified;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public Product setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public boolean isInStock() {
        return inStock;
    }

    public Product setInStock(boolean inStock) {
        this.inStock = inStock;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public Product setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public long getStars() {
        return stars;
    }

    public Product setStars(long stars) {
        this.stars = stars;
        return this;
    }

    public long getVoted() {
        return voted;
    }

    public Product setVoted(long voted) {
        this.voted = voted;
        return this;
    }
}
