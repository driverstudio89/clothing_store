package bg.softuni.clothing_store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@Getter
@Setter
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

}
