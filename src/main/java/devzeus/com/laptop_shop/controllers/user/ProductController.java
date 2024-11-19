package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.dtos.responses.ProductResponse;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.services.classes.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String products(Model model) {
        model.addAttribute("pageTitle", "Products");
        return "/user/product";
    }

    @GetMapping("/detail-product/id")
    public String detailProduct(Model model,
                                @RequestParam Long id) {
        ProductResponse product = productService.getProductResponse(id);
        Product productEntity = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("productE", productEntity);
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


}
