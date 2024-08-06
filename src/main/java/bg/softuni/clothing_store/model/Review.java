package bg.softuni.clothing_store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne(targetEntity = User.class)
    private User author;

    private String content;

    private LocalDate created;

    private int rating;

    @ManyToOne(targetEntity = Product.class)
    private Product product;

    public long getId() {
        return id;
    }

    public Review setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Review setTitle(String title) {
        this.title = title;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Review setAuthor(User author) {
        this.author = author;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Review setContent(String content) {
        this.content = content;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Review setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Review setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Review setProduct(Product product) {
        this.product = product;
        return this;
    }
}
