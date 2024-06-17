package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Category;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(CategoryType category);

}
