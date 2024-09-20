package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
