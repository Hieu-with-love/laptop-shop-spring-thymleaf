package devzeus.com.laptop_shop.controllers.admin;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.models.Role;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.services.classes.RoleService;
import devzeus.com.laptop_shop.services.classes.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserManagerController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping()
    public String showAccounts(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/user-list";
    }

    @GetMapping("/create")
    public String showCreateAccount(Model model) {
        List<Role> roles = roleService.getAllRoles();
        boolean isUser = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("user"));
        Long id = roles.stream().filter(u -> u.getName().equalsIgnoreCase("user"))
                .findFirst().get().getId();
        model.addAttribute("isUser", isUser);
        model.addAttribute("id", id);
        model.addAttribute("roles", roles);
        model.addAttribute("userDto", new UserDTO());
        return "admin/user/user-add";
    }

    @PostMapping("/create")
    public String createAccount(Model model,
                                @Valid @ModelAttribute("userDto") UserDTO userDto,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "admin/user/user-add";
        }
        boolean isCreated = userService.createUser(userDto, result);
        if (!isCreated) {
            return "admin/user/user-add";
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/update")
    public String showUpdateAccount(Model model,
                                    @RequestParam("id") Long accountId) {
        List<Role> roles = roleService.getAllRoles();
        boolean isUser = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("user"));
        Long id = roles.stream().filter(u -> u.getName().equalsIgnoreCase("user"))
                .findFirst().get().getId();
        UserDTO userDTO = userService.getUserById(accountId);
        model.addAttribute("isUser", isUser);
        model.addAttribute("id", id);
        model.addAttribute("roles", roles);
        model.addAttribute("userDto", userDTO);
        return "admin/user/user-update";
    }

    @PostMapping("/update")
    public String updateAccount(Model model,
                                @Valid @ModelAttribute UserDTO userDTO,
                                BindingResult result) {

        return "redirect:/admin/user";
    }

    @GetMapping("/disable")
    public String disableAccount(@RequestParam("id") Long accountId) {
        User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.getId().equals(accountId)) {
            return "redirect:/admin/user?error=true";
        }
        userService.disableUser(accountId);
        return "redirect:/admin/user";
    }

    @GetMapping("/delete")
    public String deleteAccount(@RequestParam("id") Long accountId) {
        userService.deleteUser(accountId);
        return "redirect:/admin/user";
    }
}
