package devzeus.com.laptop_shop.controllers.admin;

import devzeus.com.laptop_shop.services.classes.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        devzeus.com.laptop_shop.models.User user = userService.getUserByEmail(username);

        session.setAttribute("userSession", user);
        model.addAttribute("user", user);
        model.addAttribute("cartId", 1);
        model.addAttribute("titleDashboard", "Dashboard");
        return "admin/index";
    }

    @GetMapping()
    public String admin(Model model) {
        model.addAttribute("pageTitle", "Home Admin");
        return "admin/index";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("pageTitle", "Categories");
        return "admin/categories/categories";
    }
}
