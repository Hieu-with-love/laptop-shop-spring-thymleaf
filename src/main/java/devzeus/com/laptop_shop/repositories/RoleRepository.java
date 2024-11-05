package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
