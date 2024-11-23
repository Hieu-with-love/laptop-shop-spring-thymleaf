package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.models.User;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IUserService {
    UserDTO getUserById(Long id);

    // register user
    boolean registerUser(UserDTO userDTO, BindingResult result);

    List<User> getAllUsers();

    User getUserByEmail(String email);


    boolean verifyAccount(String token);

    void sendEmailToVerifyAccount(String name, String to, String token);

    void changePassword(String email);

    // soft delete
    void toggleUserActivation(String phoneNumber, boolean isActive);


    boolean existingEmail(String email);

    boolean createUser(UserDTO userDTO, BindingResult result);

    boolean updateUser(UserDTO user, BindingResult result);

    void disableUser(Long id);

    // hard delete
    void deleteUser(Long id);


}
