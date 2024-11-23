package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.models.User;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IUserService {
    // register user
    boolean registerUser(UserDTO userDTO, BindingResult result);

    List<User> getAllUsers();

    User getUserByEmail(String email);


    boolean verifyAccount(String token);

    void sendEmailToVerifyAccount(String name, String to, String token);

    void changePassword(String email);

    // soft delete
    void toggleUserActivation(String phoneNumber, boolean isActive);

    // hard delete
    void deleteUser(String email);

    boolean existingEmail(String email);

    boolean createUser(UserDTO userDTO, BindingResult result);

    boolean updateUser(UserDTO user, BindingResult result);

    boolean disableUser(Long id, BindingResult result);

}
