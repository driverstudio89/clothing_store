package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.RoleRepository;
import bg.softuni.clothing_store.model.Role;
import bg.softuni.clothing_store.model.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitRoles{

    private final RoleRepository roleRepository;

    public void run(String... args) throws Exception {
        if (roleRepository.count() > 0) {
            return;
        }
        for (UserRole roleType : UserRole.values()) {
            Role role = new Role(roleType);
            roleRepository.save(role);
        }
    }
}
