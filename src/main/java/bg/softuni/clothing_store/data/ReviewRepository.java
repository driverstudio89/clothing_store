package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Set<Review> findAllByProductId(long productId);
}
