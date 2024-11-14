package devzeus.com.laptop_shop.controllers;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.services.classes.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        devzeus.com.laptop_shop.models.User user = userService.getUserByEmail(username);
        session.setAttribute("userSession", user);
        model.addAttribute("user", user);
        model.addAttribute("titleDashboard", "Dashboard");
        return "admin/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("myUser", new UserDTO());
        return "login/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("userRegister", new UserDTO());
        return "login/register";
    }

    @PostMapping("/register")
    public String register(Model model,
                           @Valid @ModelAttribute("userRegister") UserDTO userDTO,
                           BindingResult result
    ) {
        if (result.hasErrors()) {
            return "login/register";
        }
        boolean isSuccess = userService.registerUser(userDTO, result);
        if (!isSuccess) {
            return "login/register";
        }
        return "redirect:/login"; // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute("userDTO") UserDTO userDTO, WebRequest request, SessionStatus status) {
        status.setComplete();
        request.removeAttribute("userDTO", WebRequest.SCOPE_SESSION);
        return "redirect:/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {

        return "login/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String getNewPassword(Model model,
                                 @RequestParam("email") String email) {
        userService.changePassword(email);
        model.addAttribute("msg", "Reset password success");
        return "redirect:/forgot-password?success=true";
    }

    @GetMapping("/verify-account")
    public String verifyAccount(Model model, @RequestParam("token") String token) {
        boolean isSuccess = userService.verifyAccount(token);
        if (!isSuccess) {
            model.addAttribute("error", "Invalid token");
            return "login/register";
        } else {
            model.addAttribute("message", "Verify successfully");
            return "redirect:/login";
        }
    }
}
