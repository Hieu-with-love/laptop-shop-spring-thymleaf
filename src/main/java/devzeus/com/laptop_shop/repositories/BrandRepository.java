package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Brand;
import devzeus.com.laptop_shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name);
}
