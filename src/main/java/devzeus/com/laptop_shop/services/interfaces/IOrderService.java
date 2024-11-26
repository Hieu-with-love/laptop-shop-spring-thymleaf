package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.CartItem;
import devzeus.com.laptop_shop.models.Order;
import devzeus.com.laptop_shop.models.Payment;
import devzeus.com.laptop_shop.models.User;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IOrderService {
    void createOrder(User user, Payment payment,
                     Long cartId,
                     Set<CartItem> cartDetailList);

    void deleteAll();

    List<Order> findAll();

    <S extends Order> S save(S entity);

    Optional<Order> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    List<Order> findAll(Sort sort);
}
