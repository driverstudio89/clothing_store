package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Status;
import bg.softuni.clothing_store.model.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findByName(StatusType name);
}
