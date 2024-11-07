package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.CartItem;

public interface ICartItemService {
    CartItem getCartItem(Long cartId, Long productId);

    void addItemsToCart(Long cartId, Long productId, int quantity);

    void updateItemFromCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);
}
