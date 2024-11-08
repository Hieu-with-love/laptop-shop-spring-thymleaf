package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.models.CartItem;
import devzeus.com.laptop_shop.models.User;
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
        cart.setTotalAmount(cart.getTotalAmount());
        return cart;
    }

    @Override
    public void createCart(User user) {
        try {
            Cart cart = Cart.builder()
                    .totalAmount(BigDecimal.ZERO)
                    .user(user)
                    .build();
            cartRepository.save(cart);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't create cart for user " + user.getEmail());
        }
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
