package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
