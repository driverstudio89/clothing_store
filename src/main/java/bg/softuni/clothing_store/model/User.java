package bg.softuni.clothing_store.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String country;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private LocalDate modified;

    @ManyToOne
    private Role role;

    //#################################################################################



}
