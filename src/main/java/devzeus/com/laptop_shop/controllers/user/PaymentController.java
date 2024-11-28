package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.configurations.VNPAYConfig;
import devzeus.com.laptop_shop.dtos.requests.PaymentDto;
import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.services.classes.CartService;
import devzeus.com.laptop_shop.services.classes.VNPAYService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final VNPAYService vnPayService;
    private final CartService cartService;

    @GetMapping("/vnpay")
    public String submitOrderWithVNPAY(HttpSession session,
                                       HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        Long cartId = (Long) session.getAttribute("cartId");
        Cart cart = cartService.getCartEntity(cartId);
        String vnpayUrl = vnPayService.createOrder(request, cart.getTotalAmount().intValue(), baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String vnpayCompleted(HttpServletRequest request,
                                 Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "user/orderSuccess" : "user/orderFail";
    }

}
