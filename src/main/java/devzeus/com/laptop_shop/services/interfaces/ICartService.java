package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.CartDTO;
import devzeus.com.laptop_shop.models.ShoppingCart;

public interface ICartService {
    boolean addCart(ShoppingCart cart);

    boolean updateCart(Long id, CartDTO cartDTO);

    boolean removeCart(ShoppingCart cart);

    boolean removeAllCarts();


}
