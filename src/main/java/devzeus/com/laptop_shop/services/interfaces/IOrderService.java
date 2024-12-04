package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.*;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IOrderService {
    List<Order> findByUserEmail(String username);

    void orderPending(Long id);

    void orderCancelled(Long id);

    void orderDelivered(Long id);

    void orderShipping(Long id);

    void createOrder(User user, Payment payment, Address address,
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
