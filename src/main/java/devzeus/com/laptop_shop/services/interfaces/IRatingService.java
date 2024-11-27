package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.RatingRequest;
import devzeus.com.laptop_shop.models.Rating;
import devzeus.com.laptop_shop.models.RatingId;

import java.util.List;
import java.util.Optional;

public interface IRatingService {
    List<Rating> findByProductId(Long productId);
    int countUser(Long productId);

    int countRatingStar(Long productId);

    List<Rating> findAll();

    <S extends Rating> S save(S entity);

    Optional<Rating> findById(RatingId ratingId);

    boolean existsById(RatingId ratingId);

    long count();

    void deleteById(RatingId ratingId);

    void deleteAll();

    boolean insert(RatingRequest ratingRequest);

    boolean checkOrderFirst(Long productId, Long userId);
}