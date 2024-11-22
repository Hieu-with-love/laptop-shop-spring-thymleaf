package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.models.Category;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.services.classes.CategoryService;
import devzeus.com.laptop_shop.services.classes.ProductService;
import devzeus.com.laptop_shop.services.classes.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String products(@RequestParam(defaultValue = "0") int pageNumber,
                           @RequestParam(defaultValue = "10") int pageSize,
                           Model model, HttpSession session) {
        Page<Product> productPage = productService.getProductsByPage(pageNumber, pageSize);
        List<Product> productsPage = productPage.getContent();
        List<Product> products = productService.getAllProducts();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals("anonymousUser")) {
            devzeus.com.laptop_shop.models.User user = userService.getUserByEmail(username);
            session.setAttribute("userSession", user);
            session.setAttribute("cartId", user.getCart().getId());
        }
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "user/home";
    }

    @GetMapping("/fragment/products")
    public String getProductsByPage(@RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    Model model) {
        Page<Product> productPage = productService.getProductsByPage(pageNumber, pageSize);
        List<Product> products = productPage.getContent();

        model.addAttribute("products", products);
        return "user/product_list :: productList"; // Trả về fragment Thymeleaf
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

    @GetMapping("/my-account")
    public String showPageMyAccount(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        devzeus.com.laptop_shop.models.User user = userService.getUserByEmail(username);
        model.addAttribute("user", user);
        return "user/my-account";
    }

    public String showAccountDetails(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        devzeus.com.laptop_shop.models.User user = userService.getUserByEmail(username);
        model.addAttribute("user", user);
        return "user/my-account";
    }

    @GetMapping("/my-account/email")
    public String myAccount(@RequestParam("email") String email, Model model) {
        User userAccount = userService.getUserByEmail(email);
        model.addAttribute("userAccount", userAccount);
        return "user/my-account";
    }

    @GetMapping("/laptops")
    public String showLaptops(Model model, @RequestParam("categoryId") Long categoryId) {
        List<Category> categories = categoryService.getAllCategories();
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        List<Product> productsByCategory = productService.getProductByCategory(categoryId);


        model.addAttribute("categories", categories);
        model.addAttribute("category", category);
        model.addAttribute("productsByCategory", productsByCategory);
        return "user/shop";
    }
}
