package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Rating;
import devzeus.com.laptop_shop.models.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    List<Rating> findByProductId(Long productId);
}
