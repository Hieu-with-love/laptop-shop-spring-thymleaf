package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.Order;
import devzeus.com.laptop_shop.repositories.OrderRepository;
import devzeus.com.laptop_shop.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findByPhoneNumber(String phoneNumber) {
        return orderRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Order> findByAddressContaining(String address) {
        return orderRepository.findByAddressContaining(address);
    }

    @Override
    public List<Order> findByTotalMoneyBetween(BigDecimal min, BigDecimal max) {
        return orderRepository.findByTotalMoneyBetween(min, max);
    }

    @Override
    public List<Order> findByOrderDate(LocalDate orderDate) {
        return orderRepository.findByOrderDate(orderDate);
    }

    @Override
    public List<Order> findAllByActive(boolean isActive) {
        return orderRepository.findAllByActive(isActive);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public <S extends Order> S save(S entity) {
        return orderRepository.save(entity);
    }

    @Override
    public Optional<Order> findById(Long aLong) {
        return orderRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return orderRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return orderRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        orderRepository.deleteById(aLong);
    }

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
