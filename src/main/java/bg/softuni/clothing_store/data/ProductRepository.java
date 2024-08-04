package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Category;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Set<Product> findTop12OrderByCreatedAfter(LocalDate createdAfter);

    Set<Product> findAllByCategory(Category category);

    Set<Product> findAllByCategoryAndSubCategory(Category category, SubCategory subCategory);

    Optional<Product> findByName(String name);
}
