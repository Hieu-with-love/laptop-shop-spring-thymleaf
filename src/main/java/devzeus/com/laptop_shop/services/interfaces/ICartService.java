package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.responses.CartResponse;
import devzeus.com.laptop_shop.dtos.responses.ImgUrlRes;
import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.models.User;

import java.math.BigDecimal;

public interface ICartService {
    CartResponse getCart(Long id);

    boolean existsCart(Long userId);

    void createCart(User user);

    void clearCart(Long id);

    void deleteCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart getCartEntity(Long id);

    Cart getCartByUserId(Long userId);

    ImgUrlRes getImageCart(String url);
}
