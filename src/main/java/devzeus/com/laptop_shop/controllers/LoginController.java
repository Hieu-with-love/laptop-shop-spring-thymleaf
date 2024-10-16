package devzeus.com.laptop_shop.controllers;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.services.classes.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userDTO")
public class LoginController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPhoneNumber = auth.getName();
        devzeus.com.laptop_shop.models.User user = userService.getUserByPhoneNumber(currentPhoneNumber);
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
        model.addAttribute("registerUser", new UserDTO());
        return "login/register";
    }

    @PostMapping("/register")
    public String register(Model model,
                           @ModelAttribute("userDTO") UserDTO userDTO) {
        // Kiểm tra nếu số điện thoại đã tồn tại
        if (userService.existingPhoneNumber(userDTO.getPhoneNumber())) {
            model.addAttribute("error", "Số điện thoại đã tồn tại");
            return "login/register";
        }
        // Đăng ký người dùng mới
        if (userService.isPassword(userDTO.getPhoneNumber(), userDTO.getPassword())) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp");
            return "login/register";
        }
        userService.registerUser(userDTO);
        return "redirect:/login"; // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute("userDTO") UserDTO userDTO, WebRequest request, SessionStatus status) {
        status.setComplete();
        request.removeAttribute("userDTO", WebRequest.SCOPE_SESSION);
        return "redirect:/login";
    }
}
