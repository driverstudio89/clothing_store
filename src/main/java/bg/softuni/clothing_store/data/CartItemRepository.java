package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.CartItem;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteById(long id);

    Optional<CartItem> findByProductIdAndUserId(long productId, long userId);

}
