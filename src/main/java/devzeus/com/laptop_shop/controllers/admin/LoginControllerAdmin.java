package devzeus.com.laptop_shop.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginControllerAdmin {
    @GetMapping("/home")
    public String home() {

        return "admin/login/index";
    }

    @GetMapping("/login")
    public String login() {

        return "admin/login/login";
    }

    @PostMapping("/register")
    public String register() {
        return "admin/login/register";
    }
}
