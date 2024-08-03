package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole name;

    public Role(UserRole name) {
        this.name = name;
    }

    public UserRole getName() {
        return name;
    }

    public Role setName(UserRole role) {
        this.name = role;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Role setId(Long id) {
        this.id = id;
        return this;
    }

    //###################################################################

}
