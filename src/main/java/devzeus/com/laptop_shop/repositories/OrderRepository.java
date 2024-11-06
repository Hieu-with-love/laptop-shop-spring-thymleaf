package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPhoneNumber(String phoneNumber);
    List<Order> findByAddressContaining(String address);
    List<Order> findByTotalMoneyBetween(BigDecimal min, BigDecimal max);
    List<Order> findByOrderDate(LocalDate orderDate);
    List<Order> findAllByActive(boolean isActive);
}
