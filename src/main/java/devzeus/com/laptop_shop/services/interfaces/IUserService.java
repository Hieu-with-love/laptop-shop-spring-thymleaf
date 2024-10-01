package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    // register user
    User registerUser(UserDTO user);

    // authenticate user with phoneNumber and password
    boolean authenticate(String username, String password);

    User getUserByPhoneNumber(String phoneNumber);

    User updateUser(UserDTO user);

    void changePassword(String phoneNumber, String newPassword);

    // soft delete
    void toggleUserActivation(String phoneNumber, boolean isActive);

    // hard delete
    void deleteUser(String phoneNumber);
}
