package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);
}
