package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Color;
import bg.softuni.clothing_store.model.Size;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    Size findBySizeName(SizeName sizeName);

}
