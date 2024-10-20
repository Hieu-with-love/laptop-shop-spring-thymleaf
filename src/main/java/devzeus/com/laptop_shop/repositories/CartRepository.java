package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<ShoppingCart, Long> {

}
