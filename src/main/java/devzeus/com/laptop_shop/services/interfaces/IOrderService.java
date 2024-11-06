package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> findByPhoneNumber(String phoneNumber);

    List<Order> findByAddressContaining(String address);

    List<Order> findByTotalMoneyBetween(BigDecimal min, BigDecimal max);

    List<Order> findByOrderDate(LocalDate orderDate);

    List<Order> findAllByActive(boolean isActive);

    List<Order> findAll();

    <S extends Order> S save(S entity);

    Optional<Order> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);
}
