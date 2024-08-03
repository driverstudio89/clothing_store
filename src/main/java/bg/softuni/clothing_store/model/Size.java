package bg.softuni.clothing_store.model;

import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sizes")
@NoArgsConstructor
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private SizeName sizeName;

    public Size(SizeName name) {
        this.sizeName = name;
    }

    public long getId() {
        return id;
    }

    public Size setId(long id) {
        this.id = id;
        return this;
    }

    public SizeName getSizeName() {
        return sizeName;
    }

    public Size setSizeName(SizeName sizeName) {
        this.sizeName = sizeName;
        return this;
    }
}
