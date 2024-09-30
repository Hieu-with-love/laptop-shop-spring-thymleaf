package devzeus.com.laptop_shop.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginControllerAdmin {
    @GetMapping("/login")
    public String login() {

        return "admin/login/index";
    }

    @PostMapping
    public String register() {
        return "admin/login/register";
    }
}
