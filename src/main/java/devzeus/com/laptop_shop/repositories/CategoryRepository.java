package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
