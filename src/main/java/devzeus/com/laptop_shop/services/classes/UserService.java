package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.exceptions.NotFoundException;
import devzeus.com.laptop_shop.models.Confirmation;
import devzeus.com.laptop_shop.repositories.ConfirmationRepository;
import devzeus.com.laptop_shop.models.Role;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.repositories.RoleRepository;
import devzeus.com.laptop_shop.repositories.UserRepository;
import devzeus.com.laptop_shop.services.interfaces.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    ConfirmationRepository confirmationRepository;
    @Lazy
    EmailService emailService;
    CartService cartService;

    private void validation(UserDTO userRequest, BindingResult result) {
        if (this.existingEmail(userRequest.getEmail())) {
            result.addError(new FieldError("userRegister", "email",
                    "Email da ton tai. Vui long nhap Email khac"));
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            result.addError(new FieldError("userRegister", "password",
                    "Mat khau khong khop"));
        }

        // Lấy day/month/year hiện tại, 11/2/2024 -> tru 15 năm -> 11/2/2009
        // giả sử một người có sinh nhật 1/2/2009 -> 1/2/2009 đã đu tuổi so voi day/month/year hiện tại
        // nên dùng isBefore (truoc rồi phủ định) chứ không dùng isAfter
        if (!userRequest.getDayOfBirth().isBefore(LocalDate.now().minusYears(15))) {
            result.addError(new FieldError("userRegister", "dateOfBirth",
                    "Bạn chưa đủ 15 tuổi"));
        }
    }

    @Override
    public boolean registerUser(UserDTO userDTO, BindingResult result) {
        validation(userDTO, result);
        if (!result.hasErrors()) {
            Role role = null;
            if (userDTO.getRoleId() == null) {
                role = roleRepository.findById(2L)
                        .orElseThrow(() -> new NotFoundException("Role not found"));
            } else {
                role = roleRepository.findById(userDTO.getRoleId())
                        .orElseThrow(() -> new NotFoundException("Role not found"));
            }
            User user = User.builder()
                    .phoneNumber(userDTO.getPhoneNumber())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .email(userDTO.getEmail())
                    .fullName(userDTO.getFullName())
                    .gender(userDTO.getGender())
                    .address(userDTO.getAddress())
                    .dayOfBirth(userDTO.getDayOfBirth())
                    .isActive(false)
                    .role(role)
                    .build();
            userRepository.save(user);

            Confirmation confirmation = Confirmation.builder()
                    .user(user)
                    .token(UUID.randomUUID().toString())
                    .createDate(LocalDateTime.now())
                    .build();
            confirmationRepository.save(confirmation);

            // TODO Verify account here
            try {
                emailService.sendEmailToVerifyAccount(user.getFullName(), user.getEmail(), confirmation.getToken());
            } catch (Exception e) {
                result.addError(new FieldError("userRegister", "failSend", "Gui email xac thuc that bai"));
                throw new RuntimeException("Send email failure " + e.getMessage());
            }
            // active account when verify successfully -> we create cart with this user
            cartService.createCart(user);
            return true;
        }
        return false;
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email"));
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role not found"));
        User existingUser = this.getUserByEmail(userDTO.getEmail());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setDayOfBirth(userDTO.getDayOfBirth());
        existingUser.setGender(userDTO.getGender());
        existingUser.setActive(userDTO.isActive());
        existingUser.setRole(role);
        return null;
    }

    @Override
    public boolean verifyAccount(String token) {
        return emailService.verifyToken(token);
    }

    @Override
    public void sendEmailToVerifyAccount(String name, String to, String token) {
        emailService.sendEmailToVerifyAccount(name, to, token);
    }

    @Override
    public void changePassword(String email) {
        emailService.sendEmailToRenewPassword(email);
    }


    @Override
    public void toggleUserActivation(String phoneNumber, boolean isActive) {

    }

    @Override
    public void deleteUser(String phoneNumber) {

    }

    @Override
    public boolean existingEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
