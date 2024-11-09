package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.models.CartItem;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.services.classes.CartItemService;
import devzeus.com.laptop_shop.services.classes.CartService;
import devzeus.com.laptop_shop.services.classes.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;

    @PostMapping("/add-to-cart")
    public String addToCart(Model model,
                            @RequestParam("cartId") Long cartId,
                            @RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity
    ) {
        cartItemService.addItemsToCart(cartId, productId, quantity);
        return "redirect:/user/cart/cart";
    }

    @GetMapping
    public String showItemsInCart(Model model, @RequestParam("cartId") Long cartId) {
        List<CartItem> items = cartItemService.getAllItemsInCart(cartId);
        model.addAttribute("items", items);
        return "user/cart/cart";
    }
}
