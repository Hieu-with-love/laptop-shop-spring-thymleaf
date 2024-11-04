package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);
}
