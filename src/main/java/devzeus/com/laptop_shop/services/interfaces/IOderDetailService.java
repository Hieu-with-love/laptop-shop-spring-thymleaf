package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.OrderDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IOderDetailService {
    List<OrderDetail> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<OrderDetail> findByQuantityBetween(Long min, Long max);

    List<OrderDetail> findByQuantity(Long quantity);

    List<OrderDetail> findByTotalMoneyBetween(BigDecimal min, BigDecimal max);

    List<OrderDetail> findAll();

    <S extends OrderDetail> S save(S entity);

    Optional<OrderDetail> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);
}
