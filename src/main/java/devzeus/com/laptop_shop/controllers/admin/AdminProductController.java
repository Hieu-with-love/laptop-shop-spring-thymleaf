package devzeus.com.laptop_shop.controllers.admin;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.services.classes.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    @GetMapping
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/products/index";
    }

    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("pageTitle", "Create Product");
        model.addAttribute("productDTO", productDTO);
        return "admin/products/CreateProduct";
    }

}
