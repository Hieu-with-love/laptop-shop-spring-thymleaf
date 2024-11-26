package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.responses.CartItemResponse;
import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.models.CartItem;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.repositories.CartItemRepository;
import devzeus.com.laptop_shop.repositories.CartRepository;
import devzeus.com.laptop_shop.services.interfaces.ICartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static devzeus.com.laptop_shop.utils.Constant.formatter;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductService productService;

    @Override
    public Set<CartItem> getAllItems(Long cartId) {
        Cart cart = cartService.getCartEntity(cartId);
        return cart.getItems();
    }

    @Override
    public List<CartItemResponse> getAllItemsInCart(Long cartId) {
        Cart cart = cartService.getCartEntity(cartId);

        // Sử dụng stream để chuyển đổi các CartItem thành CartItemResponse
        return cart.getItems().stream()
                .map(item -> {
                    CartItemResponse response = new CartItemResponse();
                    response.setQuantity(item.getQuantity());
                    response.setProductName(item.getProduct().getName());
                    response.setImages(item.getProduct().getThumbnail());  // Giả sử có phương thức getImage() để lấy ảnh sản phẩm
                    // Kiểm tra xem unitPrice và totalPrice có phải là null không
                    String formattedUnitPrice = item.getUnitPrice() != null
                            ? formatter.format(item.getUnitPrice())
                            : "0";  // Hoặc một giá trị mặc định
                    response.setUnitPrice(formattedUnitPrice);

                    String formattedTotalPrice = item.getTotalPrice() != null
                            ? formatter.format(item.getTotalPrice())
                            : "0";  // Hoặc một giá trị mặc định
                    response.setTotalPrice(formattedTotalPrice);
                    response.setProductId(item.getProduct().getId());

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCartEntity(cartId);
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
    }


    @Override
    @Transactional
    public void addItemsToCart(Long cartId, Long productId, int quantity) {
        // We need add items into cart careful,
        // 1. Get Cart and Product, check they exists
        // 2. Check if product exists in cart yet?
        // 3. If existing, we increase quantity
        // 4. If not exists, we create item then add into cart
        try {
            Cart cart = cartService.getCartEntity(cartId);
            Product product = productService.getProductById(productId);

            CartItem cartItemExisting = this.getCartItem(cartId, productId);

            if (cartItemExisting.getId() == null) {
                cartItemExisting.setCart(cart);
                cartItemExisting.setProduct(product);
                cartItemExisting.setQuantity(quantity);
                cartItemExisting.setUnitPrice(product.getPrice());
            } else {
                cartItemExisting.setQuantity(cartItemExisting.getQuantity() + quantity);
            }
            cartItemExisting.setTotalPrice();
            cart.addItem(cartItemExisting);
            cartItemRepository.save(cartItemExisting);
        } catch (Exception e) {
            throw new RuntimeException("Can't add Item into Cart.\nBecause " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCartEntity(cartId);
        // update value for item in cart
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCartEntity(cartId);
        CartItem itemToRemove = this.getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartItemRepository.delete(itemToRemove);
    }

    @Override
    public void updateTotalAmount(Long cartId, Long productId) {
        Cart cart = cartService.getCartEntity(cartId);
        BigDecimal totalAmount = cart.getItems().stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null) {
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void decQty(Long cartId, Long productId) {
        CartItem itemToDec = this.getCartItem(cartId, productId);
        itemToDec.setQuantity(itemToDec.getQuantity() - 1);
        itemToDec.setTotalPrice();
        this.updateTotalAmount(cartId, productId);
        cartItemRepository.save(itemToDec);
    }

    @Override
    @Transactional
    public void incQty(Long cartId, Long productId) {
        CartItem itemToInc = this.getCartItem(cartId, productId);
        itemToInc.setQuantity(itemToInc.getQuantity() + 1);
        itemToInc.setTotalPrice();
        this.updateTotalAmount(cartId, productId);
        cartItemRepository.save(itemToInc);
    }


}
