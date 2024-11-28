package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.dtos.responses.CartItemResponse;
import devzeus.com.laptop_shop.dtos.responses.CartResponse;
import devzeus.com.laptop_shop.models.*;
import devzeus.com.laptop_shop.services.classes.UserService;
import devzeus.com.laptop_shop.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/checkout")
public class CheckoutController {
    private final ICartService cartService;
    private final ICartItemService cartDetailService;
    private final IOrderService orderService;
    private final UserService userService;
    private final IAddressService addressService;
    private final IPaymentService paymentService;

    @GetMapping("")
    public String checkout(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(username);
        List<Address> addresses = addressService.findByUser_Username(username);
        Long cartId = (Long) session.getAttribute("cartId");
        CartResponse cart = cartService.getCart(cartId);
        Cart cartEntity = cartService.getCartEntity(cartId);
        List<CartItemResponse> cartDetailList = cartDetailService.getAllItemsInCart(cartId);

        model.addAttribute("user", user);
        model.addAttribute("addresses", addresses);
        model.addAttribute("cart", cart);
        model.addAttribute("cartEntity", cartEntity);
        model.addAttribute("cartDetailList", cartDetailList);

        return "user/checkout";
    }

    @PostMapping("/process")
    @ResponseBody
    public String process(Model model, HttpSession session,
                          @RequestParam Map<String, String> params) {
        // Get the current user
        // Get value from json data
        Long addressId = Long.parseLong(params.get("addressId"));
        String paymentMethod = params.get("paymentMethod");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(username);

        // Find the address by its ID
        Address address = addressService.findById(addressId).get();
        // Find the payment method by its name
        Payment payment = paymentService.findByType(paymentMethod);
        // Retrieve the cart detail list from the session
        Long cartId = (Long) session.getAttribute("cartId");
        Cart cart = cartService.getCartEntity(cartId);
        Set<CartItem> cartDetailList = cartDetailService.getAllItems(cartId);
        // Update the cart's total price

        orderService.createOrder(user, payment, address, cart.getId(), cartDetailList);

        String redirectUrl;
        if (payment.getType().equals("paypal")) {
            redirectUrl = "/user/payment/paypal";
        } else if (payment.getType().equals("vnpay")) {
            redirectUrl = "/user/payment/vnpay";
        } else {
            // Handle other payment methods or default case
            redirectUrl = "/user";
        }

        // Return the redirect URL as a JSON string
        return "{\"redirectUrl\": \"" + redirectUrl + "\"}";
    }
}
