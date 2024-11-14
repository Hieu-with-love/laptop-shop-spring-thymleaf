package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.Confirmation;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.repositories.ConfirmationRepository;
import devzeus.com.laptop_shop.repositories.UserRepository;
import devzeus.com.laptop_shop.services.interfaces.IEmailService;
import devzeus.com.laptop_shop.utils.EmailUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailService implements IEmailService {
    @Value("${spring.mail.verify.host}")
    String host;
    @Value("${spring.mail.username}")
    String fromEmail;

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final ConfirmationRepository confirmationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendEmailToVerifyAccount(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Verify New User Account");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.verifyAccount(name, host, token));
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Has error while sending email to verify account\n\n" + e.getMessage());
        }
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Confirmation confirm = confirmationRepository.findByToken(token)
                    .orElseThrow(() -> new RuntimeException("Token invalid"));
            User user = userRepository.findByEmailIgnoreCase(confirm.getUser().getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setActive(true);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void sendEmailToRenewPassword(String email) {
        try {
            // Get user exists to confirm have user
            User userExisting = userRepository.findByEmailIgnoreCase(email)
                    .orElseThrow(() -> new RuntimeException("Email to get new password not found"));
            String newPassword = EmailUtils.generateRandomPassword();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Change Password");
            message.setFrom(fromEmail);
            message.setTo(email);
            String content = "New your password is: " + newPassword
                    + "\n\nSupport by [devzeus]";
            message.setText(content);
            mailSender.send(message);
            // Set password when send success
            userExisting.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(userExisting);

        } catch (Exception e) {
            throw new RuntimeException("Has error while sending email to renew password\n" + e.getMessage());
        }
    }
}
