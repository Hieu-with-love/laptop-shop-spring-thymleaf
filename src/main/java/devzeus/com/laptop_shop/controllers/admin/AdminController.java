package devzeus.com.laptop_shop.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping()
    public String admin(Model model) {
        model.addAttribute("pageTitle", "Home Admin");
        return "index";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("pageTitle", "Categories");
        return "admin/cruds/categories";
    }
}
