package devzeus.com.laptop_shop.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/products")
public class ProductController {
    @GetMapping
    public String products(Model model) {
        model.addAttribute("pageTitle", "Products");
        return "/user/product";
    }
    
}
