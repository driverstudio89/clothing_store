package bg.softuni.clothing_store.data;

import bg.softuni.clothing_store.model.Role;
import bg.softuni.clothing_store.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(UserRole role);
}
