package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.repositories.CartItemRepository;
import devzeus.com.laptop_shop.repositories.CartRepository;
import devzeus.com.laptop_shop.services.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id = " + id));
        // assign total amount for cart = 0
        cart.setTotalAmount(cart.getTotalAmount());
        return cart;
    }

    @Override
    public boolean addToCart(Cart cart) {
        return false;
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = this.getCart(id);
        // We clear cart by clear items in that cart. and set total amount = 0
        cart.getItems().clear();
        cart.setTotalAmount(cart.getTotalAmount());
        cartItemRepository.deleteAllByCartId(id);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return this.getCart(id).getTotalAmount();
    }
}
