package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> searchProducts(String keyword);

    Optional<Product> findByName(String name);
}
