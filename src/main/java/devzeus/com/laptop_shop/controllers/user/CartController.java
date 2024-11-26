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
        model.addAttribute("cartEmpty", items.isEmpty());
        return "user/cart";
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
