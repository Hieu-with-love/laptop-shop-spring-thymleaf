package devzeus.com.laptop_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class HomeController {
    @GetMapping
    public String products(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "user/index";
    }
}
