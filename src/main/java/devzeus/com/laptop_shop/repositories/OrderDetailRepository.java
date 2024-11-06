package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<OrderDetail> findByQuantityBetween(Long min, Long max);
    List<OrderDetail> findByQuantity(Long quantity);
    List<OrderDetail> findByTotalMoneyBetween(BigDecimal min, BigDecimal max);

}
