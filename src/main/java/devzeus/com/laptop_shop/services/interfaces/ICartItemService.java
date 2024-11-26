package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.responses.CartItemResponse;
import devzeus.com.laptop_shop.models.CartItem;

import java.util.List;
import java.util.Set;

public interface ICartItemService {
    Set<CartItem> getAllItems(Long cartId);

    List<CartItemResponse> getAllItemsInCart(Long cartId);

    CartItem getCartItem(Long cartId, Long productId);

    void addItemsToCart(Long cartId, Long productId, int quantity);

    void updateItemQuantity(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateTotalAmount(Long cartId, Long productId);

    void decQty(Long cartId, Long productId);

    void incQty(Long cartId, Long productId);
}
