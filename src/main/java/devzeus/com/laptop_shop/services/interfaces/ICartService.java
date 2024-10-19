package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.CartDTO;
import devzeus.com.laptop_shop.models.Cart;
import org.springframework.stereotype.Service;

public interface ICartService {
    boolean addCart(Cart cart);

    boolean updateCart(Long id, CartDTO cartDTO);

    boolean removeCart(Cart cart);

    boolean removeAllCarts();

    
}
