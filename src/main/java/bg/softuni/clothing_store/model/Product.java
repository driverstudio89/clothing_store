package bg.softuni.clothing_store.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private LocalDate modified;

    @OneToMany(targetEntity = Picture.class, mappedBy = "product")
    private Set<Picture> pictures;

    @ManyToOne
    private Category category;




}
