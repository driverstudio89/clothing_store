package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.ColorName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "colors")
@Getter
@Setter
@NoArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorName colorName;

    public Color(ColorName name) {
        this.colorName = name;

    }
}
