package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

public interface IUserService {
    // register user
    boolean registerUser(UserDTO userDTO, BindingResult result);

    User getUserByEmail(String email);

    User updateUser(UserDTO user);

    boolean verifyAccount(String token);

    void sendEmailToVerifyAccount(String name, String to, String token);

    void changePassword(String email);

    // soft delete
    void toggleUserActivation(String phoneNumber, boolean isActive);

    // hard delete
    void deleteUser(String email);

    boolean existingEmail(String email);
}
