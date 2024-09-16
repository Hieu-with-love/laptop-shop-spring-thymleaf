package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
