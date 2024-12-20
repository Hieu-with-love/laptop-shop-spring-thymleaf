package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.requests.RatingRequest;
import devzeus.com.laptop_shop.models.*;
import devzeus.com.laptop_shop.repositories.OrderItemRepository;
import devzeus.com.laptop_shop.repositories.ProductRepository;
import devzeus.com.laptop_shop.repositories.RatingRepository;
import devzeus.com.laptop_shop.repositories.UserRepository;
import devzeus.com.laptop_shop.services.interfaces.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements IRatingService {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderDetailRepository;

    @Override
    public List<Rating> findByProductId(Long productId) {
        return ratingRepository.findByProductId(productId);
    }

    @Override
    public int countUser(Long productId) {
        return ratingRepository.findByProductId(productId).size();
    }

    @Override
    public int countRatingStar(Long productId) {
        List<Rating> ratings = ratingRepository.findByProductId(productId);
        if (ratings.isEmpty())
            return 0;
        int sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getStar();
        }
        return sum / ratings.size();
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public <S extends Rating> S save(S entity) {
        return ratingRepository.save(entity);
    }

    @Override
    public Optional<Rating> findById(RatingId ratingId) {
        return ratingRepository.findById(ratingId);
    }

    @Override
    public boolean existsById(RatingId ratingId) {
        return ratingRepository.existsById(ratingId);
    }

    @Override
    public long count() {
        return ratingRepository.count();
    }

    @Override
    public void deleteById(RatingId ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public void deleteAll() {
        ratingRepository.deleteAll();
    }

    @Override
    public boolean insert(RatingRequest ratingRequest){
        try {
            Product product = productRepository.findById(ratingRequest.getProductId()).orElse(null);
            User user = userRepository.findById(ratingRequest.getUserId()).orElse(null);
            if(product == null||user == null){
                return false;
            }
            Rating rating = new Rating(new RatingId(user.getId(),product.getId()),
                    ratingRequest.getContent(),ratingRequest.getStar(),user,product);

            ratingRepository.save(rating);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkOrderFirst(Long productId, Long userId) {
        List<OrderItem> orderDetails = orderDetailRepository.findByProductId(productId);

        if(orderDetails.size() > 0){
            for(OrderItem orderDetail : orderDetails){
                if (orderDetail.getOrder().getUser().getId().equals(userId)){
                    return true;
                }
            }
        }
        return false;
    }
}