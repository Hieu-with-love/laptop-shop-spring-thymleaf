package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.CartItem;

import java.util.List;

public interface ICartItemService {
    List<CartItem> getAllItemsInCart(Long cartId);

    CartItem getCartItem(Long cartId, Long productId);

    void addItemsToCart(Long cartId, Long productId, int quantity);

    void updateItemQuantity(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);
}
