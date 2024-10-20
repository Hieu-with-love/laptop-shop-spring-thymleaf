package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.services.classes.ProductService;
import devzeus.com.laptop_shop.services.classes.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class HomeController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public String products(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("pageTitle", "Home");
        return "user/product/index";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        // Ki
        return "login/forgot-password";
    }

    @GetMapping("/about-us")
    public String showAboutUs(Model model) {

        return "user/about-us";
    }

    @GetMapping("/my-account/email")
    public String myAccount(@RequestParam("email") String email, Model model) {
        User userAccount = userService.getUserByEmail(email);
        model.addAttribute("userAccount", userAccount);
        return "user/my-account";
    }
}
