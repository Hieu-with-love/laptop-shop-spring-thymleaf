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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    ConfirmationRepository confirmationRepository;
    EmailService emailService;
    CartService cartService;

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        if (user.getId() == null) {
            return null;
        }
        return UserDTO.builder()
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .address(user.getAddress())
                .dayOfBirth(user.getDayOfBirth())
                .isActive(user.isActive())
                .roleId(user.getRole().getId())
                .avatar(user.getAvatar())
                .build();
    }

    @Override
    public UserDTO getUserDTO(String email) {
        User user = userRepository.findByEmail(email).orElse(new User());
        if (user.getId() == null) {
            return null;
        }
        return UserDTO.builder()
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .address(user.getAddress())
                .dayOfBirth(user.getDayOfBirth())
                .isActive(user.isActive())
                .roleId(user.getRole().getId())
                .avatar(user.getAvatar())
                .build();
    }

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
        if (userRequest.getDayOfBirth() != null && !userRequest.getDayOfBirth().isBefore(LocalDate.now().minusYears(15))) {
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
                List<Role> roles = roleRepository.findAll();
                Long roleIdUser = roles.stream().filter(r -> r.getName().equalsIgnoreCase("user"))
                        .findFirst().get().getId();
                role = roleRepository.findById(roleIdUser)
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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email"));
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
    public boolean changePassword(UserDTO userDTO, BindingResult result) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            result.addError(new FieldError("userDto", "password", "Mật khẩu không khớp!"));
        }
        if (!result.hasErrors()) {
            User user = this.getUserByEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }


    @Override
    public void toggleUserActivation(String phoneNumber, boolean isActive) {

    }

    @Override
    public boolean existingEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean catchException(UserDTO userDTO, BindingResult result) {
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElse(new Role());

        if (role.getId() == null) {
            result.addError(new FieldError("user-manage", "role", "Khong ton tai role"));
        }

        if (this.existingEmail(userDTO.getEmail())) {
            result.addError(new FieldError("user-manage", "email",
                    "Email da ton tai. Vui long nhap Email khac"));
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            result.addError(new FieldError("user-manage", "password", "Mat khau khong khop"));
        }

        if (!userDTO.getDayOfBirth().isBefore(LocalDate.now().minusYears(15))) {
            result.addError(new FieldError("user-manage", "dateOfBirth",
                    "Bạn chưa đủ 15 tuổi"));
        }

        return result.hasErrors();
    }

    @Override
    public boolean createUser(UserDTO userDTO, BindingResult result) {
        if (catchException(userDTO, result))
            return false;

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElse(new Role());
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .phoneNumber(userDTO.getPhoneNumber())
                .fullName(userDTO.getFullName())
                .gender(userDTO.getGender())
                .address(userDTO.getAddress())
                .dayOfBirth(userDTO.getDayOfBirth())
                .isActive(true)
                .role(role)
                .avatar(userDTO.getAvatar())
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateUser(UserDTO userDTO, BindingResult result) {
        if (catchException(userDTO, result))
            return false;

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
        userRepository.save(existingUser);

        return true;
    }

    @Override
    public boolean updateProfile(UserDTO userRequest, BindingResult result) {
        // logic update user
        validation(userRequest, result);
        try {
            User existingUser = userRepository.findByEmailIgnoreCase(userRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            existingUser.setFullName(userRequest.getFullName());
            existingUser.setGender(userRequest.getGender());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setPhoneNumber(userRequest.getPhoneNumber());
            existingUser.setGender(userRequest.getGender());
            existingUser.setDayOfBirth(userRequest.getDayOfBirth());
            existingUser.setAvatar(existingUser.getAvatar());

            userRepository.save(existingUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElse(new User());

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
