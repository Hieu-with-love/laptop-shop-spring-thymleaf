package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.dtos.requests.RatingRequest;
import devzeus.com.laptop_shop.dtos.responses.ProductResponse;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.services.classes.ProductService;
import devzeus.com.laptop_shop.services.interfaces.IRatingService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/products")
public class ProductController {
    private final ProductService productService;
    private final IRatingService ratingService;

    @GetMapping("/detail-product/id")
    public String detailProduct(Model model,
                                @RequestParam Long id) {
        ProductResponse product = productService.getProductResponse(id);
        Product productEntity = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("productE", productEntity);
        model.addAttribute("productImages", productService.getProductById(id).getProductImages());
        model.addAttribute("ratings", ratingService.findByProductId(id));
        model.addAttribute("ratingCount", ratingService.countRatingStar(id));
        model.addAttribute("ratingUser", ratingService.countUser(id));
        model.addAttribute("rating", new RatingRequest());
        return "/user/product/detail";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {

        return "/user/product/cart";
    }

    @PostMapping("/add-to-card/id")
    public String addToCart(Model model) {

        return "/user/product/cart";
    }

    @PostMapping("/reviews")
    public String reviews(@Valid @ModelAttribute("rating") RatingRequest ratingRequest,
                          @RequestParam("productId") Long productId, HttpSession session,
                          RedirectAttributes redirectAttributes) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            User user = (User) session.getAttribute("userSession");
            ratingRequest.setProductId(productId);
            ratingRequest.setUserId(user.getId());

            if (ratingService.checkOrderFirst(productId, user.getId())) {
                if (!ratingService.insert(ratingRequest)) {
                    String msg = "Not found user/product";
                    redirectAttributes.addFlashAttribute("msg", msg);
                }
            } else {
                String msg = "You need to buy first";
                redirectAttributes.addFlashAttribute("msg", msg);
            }
        }
        return "redirect:/user/products/detail-product/id?id=" + productId;    }
}
