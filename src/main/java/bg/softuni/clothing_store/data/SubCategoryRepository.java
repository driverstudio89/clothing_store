package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Category;
import bg.softuni.clothing_store.model.SubCategory;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    SubCategory findBySubCategory(SubCategoryType subCategoryType);
}
