package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.CartItem;
import devzeus.com.laptop_shop.services.interfaces.ICartItemService;

public class CartItemService implements ICartItemService {

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        return null;
    }

    @Override
    public void addItemsToCart(Long cartId, Long productId, int quantity) {
        // We need add items into cart careful,
    }

    @Override
    public void updateItemFromCart(Long cartId, Long productId, int quantity) {

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

    }
}
