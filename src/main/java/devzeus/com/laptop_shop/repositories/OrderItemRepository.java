package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByProductId(long productId);
}
