package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.dtos.responses.CartItemResponse;
import devzeus.com.laptop_shop.dtos.responses.CartResponse;
import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.services.classes.CartItemService;
import devzeus.com.laptop_shop.services.classes.CartService;
import devzeus.com.laptop_shop.services.classes.ProductService;
import devzeus.com.laptop_shop.services.classes.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public String showItemsInCart(Model model, @RequestParam("cartId") Long cartId) {
        CartResponse cartResponse = cartService.getCart(cartId);
        List<CartItemResponse> items = cartItemService.getAllItemsInCart(cartId);
        model.addAttribute("listItems", items);
        model.addAttribute("cart", cartResponse);
        return "user/cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(Model model,
                            @RequestParam("cartId") Long cartId,
                            @RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity
    ) {
        cartItemService.addItemsToCart(cartId, productId, quantity);
        cartItemService.updateTotalAmount(cartId, productId);
        return "redirect:/user/cart?cartId=" + cartId;
    }

    @PostMapping("/delete-item")
    public String deleteItemFromCart(Model model,
                                     @RequestParam("cartId") Long cartId,
                                     @RequestParam("productName") String productName) {
        Product product = productService.getProductByName(productName);
        cartItemService.removeItemFromCart(cartId, product.getId());
        model.addAttribute("msg", "Removing item from cart successfully");
        return "redirect:/user/cart?cartId=" + cartId;
    }

    @GetMapping("/clearCart")
    public String clearCart(Model model, @RequestParam("cartId") Long cartId) {
        cartService.clearCart(cartId);
        model.addAttribute("msg", "Clearing cart successfully");
        return "redirect:/user/cart?cartId=" + cartId;
    }

    @PostMapping("/dec-qty")
    public String decQty(@RequestParam("cartId") Long cartId,
                         @RequestParam("productName") String productName
    ) {
        Product product = productService.getProductByName(productName);
        cartItemService.decQty(cartId, product.getId());
        return "redirect:/user/cart?cartId=" + cartId;
    }

    @PostMapping("/inc-qty")
    public String incQty(@RequestParam("cartId") Long cartId,
                         @RequestParam("productName") String productName
    ) {
        Product product = productService.getProductByName(productName);
        cartItemService.incQty(cartId, product.getId());
        return "redirect:/user/cart?cartId=" + cartId;
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model,
                               @RequestParam("cartId") Long cartId) {
        Cart cart = cartService.getCartEntity(cartId);
        model.addAttribute("cart", cart);
        return "user/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(Model model,
                           @RequestParam("orderId") Long orderId) {
        return "redirect:user/cart?cartId=" + orderId;
    }

}
