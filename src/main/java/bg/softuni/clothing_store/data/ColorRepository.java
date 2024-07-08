package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Color;
import bg.softuni.clothing_store.model.enums.ColorName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    Color findByColorName(ColorName colorName);
}
