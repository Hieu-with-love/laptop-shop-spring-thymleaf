package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.services.classes.CartItemService;
import devzeus.com.laptop_shop.services.classes.CartService;
import devzeus.com.laptop_shop.services.classes.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.*;

@RestController
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class CartRestController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final ProductService productService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestParam Map<String, String> params) {
        try {
            Long cartId = Long.parseLong(params.get("cartId"));
            Long productId = Long.parseLong(params.get("productId"));
            int quantity = Integer.parseInt(params.get("quantity"));
            cartItemService.addItemsToCart(cartId, productId, quantity);
            cartItemService.updateTotalAmount(cartId, productId);
            return ResponseEntity.ok("Thêm sản phẩm vào giỏ hàng thành công !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Không thêm được, thu lại");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteItemFromCart(@RequestParam Map<String, String> params) {
        try {
            Long productId = Long.parseLong(params.get("productId"));
            Long cartId = Long.parseLong(params.get("cartId"));
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok("Xoá thanh công.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xoá không thanh công. Thử lại !");
        }
    }


    @PostMapping("/clearCart")
    public ResponseEntity<?> clearCart(@RequestParam Map<String, String> params) {
        try {
            Long cartId = Long.parseLong(params.get("cartId"));
            cartService.clearCart(cartId);
            return ResponseEntity.ok("Clearing cart successfully !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Clear không thanh công. Thử lại !");
        }
    }

    @PostMapping("/dec-qty")
    public ResponseEntity<String> decQty(@RequestParam Map<String, String> params) {
        try {
            Long productId = Long.parseLong(params.get("productId"));
            Long cartId = Long.parseLong(params.get("cartId"));
            cartItemService.decQty(cartId, productId);
            return ResponseEntity.ok("DecQty successfully !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Clear không thanh công. Thử lại !");
        }
    }

    @PostMapping("/inc-qty")
    public ResponseEntity<String> incQty(@RequestParam Map<String, String> params) {
        try {
            Long productId = Long.parseLong(params.get("productId"));
            Long cartId = Long.parseLong(params.get("cartId"));

            cartItemService.incQty(cartId, productId);
            return ResponseEntity.ok("IncQty successfully !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Clear không thanh công. Thử lại !");
        }
    }
}