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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SecurityConfig securityConfig;
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
                .password(securityConfig.passwordEncoder().encode(userDTO.getPassword()))
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
    public boolean authenticate(String email, String password) {
        User existingUser = this.getUserByEmail(email);
        String passwordEncoder = securityConfig.passwordEncoder().encode(password);
        return existingUser != null && existingUser.getPassword().equals(passwordEncoder);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public boolean isPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user.getPassword().equals(password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        devzeus.com.laptop_shop.models.User user = userRepository.findByEmail(email);

        // get user's role, exchange to GrantedAuthority
        String role = "ROLE_" + user.getRole().getName().toUpperCase();
        // create authenticate for user
        GrantedAuthority auth = new SimpleGrantedAuthority(role);
        // return granted user
        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                Collections.singletonList(auth)
        );
    }
}
