package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.configurations.SecurityConfig;
import devzeus.com.laptop_shop.dtos.requests.UserDTO;
import devzeus.com.laptop_shop.exceptions.NotFoundException;
import devzeus.com.laptop_shop.models.Role;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.repositories.RoleRepository;
import devzeus.com.laptop_shop.repositories.UserRepository;
import devzeus.com.laptop_shop.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDTO userDTO) {
        Role role = null;
        if (userDTO.getRoleId() == null) {
            role = roleRepository.findById(1L)
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
                .isActive(true)
                .role(role)
                .build();
        return userRepository.save(user);
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
    public void changePassword(String phoneNumber, String newPassword) {

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
