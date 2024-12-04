package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.enums.OrderStatus;
import devzeus.com.laptop_shop.models.*;
import devzeus.com.laptop_shop.repositories.OrderRepository;
import devzeus.com.laptop_shop.services.interfaces.ICartService;
import devzeus.com.laptop_shop.services.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ICartService cartService;

    @Override
    public List<Order> findByUserEmail(String username) {
        return orderRepository.findByUserEmail(username);
    }

    @Override
    public void orderPending(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
    }

    @Override
    public void orderCancelled(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public void orderDelivered(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
    }

    @Override
    public void orderShipping(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.SHIPPING);
        orderRepository.save(order);
    }

    @Override
    public void createOrder(User user, Payment payment, Address address,
                            Long cartId,
                            Set<CartItem> cartDetailList) {
        Cart cart = cartService.getCartEntity(cartId);
        Order order = new Order();
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        order.setPayment(payment);
        order.setAddress(address);
        Set<OrderItem> orderDetails = new HashSet<>();
        for (CartItem cartDetail : cartDetailList) {
            OrderItem orderDetail = new OrderItem();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getTotalPrice());
            orderDetails.add(orderDetail);
        }
        order.setOrderItems(orderDetails);
        orderRepository.save(order);
        cartService.clearCart(cartId);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
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

    @Override
    public List<Order> findAll(Sort sort) {
        return orderRepository.findAll(sort);
    }
}
