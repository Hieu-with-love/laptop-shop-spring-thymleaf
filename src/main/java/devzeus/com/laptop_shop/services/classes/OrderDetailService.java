package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.OrderDetail;
import devzeus.com.laptop_shop.repositories.OrderDetailRepository;
import devzeus.com.laptop_shop.services.interfaces.IOderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService implements IOderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findByPriceBetween(BigDecimal min, BigDecimal max) {
        return orderDetailRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<OrderDetail> findByQuantityBetween(Long min, Long max) {
        return orderDetailRepository.findByQuantityBetween(min, max);
    }

    @Override
    public List<OrderDetail> findByQuantity(Long quantity) {
        return orderDetailRepository.findByQuantity(quantity);
    }

    @Override
    public List<OrderDetail> findByTotalMoneyBetween(BigDecimal min, BigDecimal max) {
        return orderDetailRepository.findByTotalMoneyBetween(min, max);
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public <S extends OrderDetail> S save(S entity) {
        return orderDetailRepository.save(entity);
    }

    @Override
    public Optional<OrderDetail> findById(Long aLong) {
        return orderDetailRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return orderDetailRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return orderDetailRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        orderDetailRepository.deleteById(aLong);
    }

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }
}
