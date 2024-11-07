package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.CartDTO;
import devzeus.com.laptop_shop.models.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);

    boolean addToCart(Cart cart);

    void clearCart(Long id);

    void deleteCart(Long id);

    BigDecimal getTotalPrice(Long id);
}
