package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.dtos.responses.ProductResponse;
import devzeus.com.laptop_shop.models.Cart;
import devzeus.com.laptop_shop.models.Category;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.services.classes.CartService;
import devzeus.com.laptop_shop.services.classes.CategoryService;
import devzeus.com.laptop_shop.services.classes.ProductService;
import devzeus.com.laptop_shop.services.classes.UserService;
import devzeus.com.laptop_shop.services.interfaces.WishlistItemService;
import devzeus.com.laptop_shop.services.interfaces.WishlistService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final WishlistService wishlistService;
    private final WishlistItemService wishlistItemService;
    private final CartService cartService;

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "0") int pageNo,
                       @RequestParam(defaultValue = "12") int pageSize,
                       Model model, HttpSession session) {
        Page<Product> productPage = productService.getProductsByPage(pageNo, pageSize);
        List<ProductResponse> products = productService.getAllProductResponses(productPage.getContent());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals("anonymousUser")) {
            devzeus.com.laptop_shop.models.User user = userService.getUserByEmail(username);
            // create wishlist if user hasn't yet
            wishlistService.createWishlist(user);
            cartService.createCart(user);
            int countWishlist = wishlistItemService.getItemsCount(user.getId());
            Long wishlistId = wishlistService.getWishlistByUserId(user.getId()).getId();
            Cart currentCart = cartService.getCartByUserId(user.getId());
            session.setAttribute("userSession", user);
            session.setAttribute("cartId", currentCart.getId());
            session.setAttribute("wishlistId", wishlistId);
            session.setAttribute("countWishlist", countWishlist);
            session.setAttribute("quantityItems", currentCart.getItems().size());
        }
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageSize", pageSize);
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
        UserDTO userDTO = userService.getUserDTO(username);
        model.addAttribute("userDto", userDTO);
        return "user/my-account";
    }

    @PostMapping("/my-account/profile")
    public String updateProfile(Model model,
                                @Valid @ModelAttribute UserDTO userDto,
                                BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("msg", "Sửa profile thất bại!");
            return "user/my-account";
        }
        boolean isUpdated = userService.updateProfile(userDto, result);
        if (isUpdated) {
            model.addAttribute("msg", "Sửa profile thành công!");
        } else {
            model.addAttribute("msg", "Sửa profile thất bại!");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO userDTO = userService.getUserDTO(username);
        model.addAttribute("userDto", userDTO);
        return "user/my-account";
    }

    @PostMapping("/my-account/password")
    public String updatePassword(Model model,
                                 @ModelAttribute UserDTO userDto,
                                 BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("msg", "Đổi mật khẩu thất bại thất bại!");
            return "user/my-account";
        }

        boolean isUpdated = userService.changePassword(userDto, result);
        if (isUpdated) {
            model.addAttribute("msg", "Đổi mật khẩu thành công!");
        } else {
            model.addAttribute("msg", "Đổi mật khẩu thất bại!");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO userDTO = userService.getUserDTO(username);
        model.addAttribute("userDto", userDTO);
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
