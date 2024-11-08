package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.models.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);

    void createCart(User user);

    void clearCart(Long id);

    void deleteCart(Long id);

    BigDecimal getTotalPrice(Long id);
}
