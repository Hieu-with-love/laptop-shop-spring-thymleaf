package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.responses.CartResponse;
import devzeus.com.laptop_shop.dtos.responses.ImgUrlRes;
import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.models.CartItem;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.repositories.CartItemRepository;
import devzeus.com.laptop_shop.repositories.CartRepository;
import devzeus.com.laptop_shop.services.interfaces.ICartService;
import devzeus.com.laptop_shop.utils.Constant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCartEntity(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id = " + id));
        cart.setTotalAmount(cart.getTotalAmount());
        return cart;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public ImgUrlRes getImageCart(String url) {
        boolean isUrlImg = url.contains("https");
        return ImgUrlRes.builder()
                .image(url)
                .isUrlImage(isUrlImg)
                .build();
    }

    @Override
    public CartResponse getCart(Long id) {
        Cart cart = getCartEntity(id);

        return CartResponse.builder()
                .totalAmount(Constant.formatter.format(cart.getTotalAmount()))
                .build();
    }

    @Override
    public boolean existsCart(Long userId) {
        return cartRepository.existsCartByUserId(userId);
    }

    @Override
    public void createCart(User user) {
        try {
            if (!existsCart(user.getId())) {
                Cart cart = Cart.builder()
                        .totalAmount(BigDecimal.ZERO)
                        .user(user)
                        .build();
                cartRepository.save(cart);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't create cart for user " + user.getEmail());
        }
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = this.getCartEntity(id);
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
        return this.getCartEntity(id).getTotalAmount();
    }
}
