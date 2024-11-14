package devzeus.com.laptop_shop.controllers;

import devzeus.com.laptop_shop.services.classes.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.*;

@RestController
@RequestMapping("${api.v1}")
@RequiredArgsConstructor
public class LoginRestController {
    private final EmailService emailService;

    @PostMapping("/renew-password")
    public ResponseEntity<?> sendEmailToRenewPassword(@RequestBody String email) {
        try {
            emailService.sendEmailToRenewPassword(email);
            return ResponseEntity.ok("Renew password is successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Renew password failure " + e.getMessage());
        }
    }

}
