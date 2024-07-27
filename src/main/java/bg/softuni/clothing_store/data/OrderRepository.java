package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Order;
import bg.softuni.clothing_store.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByCreatedDesc();

    List<Order> findAllByCreated(LocalDate created);

    List<Order> findAllByStatus(Status status);

    List<Order> findAllByStatusAndCreated(Status status, LocalDate created);

    List<Order> findAllByUserIdOrderByCreatedDesc(Long userId);


}
