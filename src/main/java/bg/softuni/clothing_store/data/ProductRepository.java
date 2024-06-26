package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Set<Product> findTop12OrderByCreatedAfter(LocalDate createdAfter);
}
