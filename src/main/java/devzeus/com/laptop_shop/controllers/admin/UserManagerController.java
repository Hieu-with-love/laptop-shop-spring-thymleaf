package devzeus.com.laptop_shop.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/manage")
public class UserManagerController {

    @GetMapping("/accounts")
    public String showAccounts(Model model) {

        return "admin/user/accounts";
    }

//    public String addAccount(Model model) {
//
//    }
//
//    public String updateAccount(Model model) {
//
//    }
//
//    public String deleteAccount(Model model) {
//
//    }
}
