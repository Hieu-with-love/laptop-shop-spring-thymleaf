package devzeus.com.laptop_shop.configurations;

import devzeus.com.laptop_shop.dtos.requests.RoleDTO;
import devzeus.com.laptop_shop.models.Role;
import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.repositories.RoleRepository;
import devzeus.com.laptop_shop.repositories.UserRepository;
import devzeus.com.laptop_shop.services.classes.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    RoleService roleService;
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            String[] roleNames = {"admin", "user", "guest"};
            for (String roleName : roleNames) {
                if (roleService.getRoleByName(roleName) == null) {
                    RoleDTO roleDTO = RoleDTO.builder()
                            .name(roleName)
                            .build();
                    roleService.createRole(roleDTO);
                    log.info("Default Role created: " + roleName);
                }
            }
            // Tạo tài khoản admin mặc định nếu chưa tồn tại

            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                Role roleAdmin = roleService.getRoleByName("admin");
                devzeus.com.laptop_shop.models.User user = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .fullName("system admin")
                        .isActive(true)
                        .role(roleAdmin)
                        .build();
                userRepository.save(user);
                log.info("Default admin user created with password:admin, please change it");
            }

            for (int i = 0; i < 10; i++) {
                if (userRepository.findByEmail("user" + i + "@gmail.com").isEmpty()) {
                    Role roleUser = roleService.getRoleByName("user");
                    devzeus.com.laptop_shop.models.User user = User.builder()
                            .email("user" + i + "@gmail.com")
                            .password(passwordEncoder.encode("123"))
                            .fullName("system")
                            .isActive(true)
                            .role(roleUser)
                            .build();
                    userRepository.save(user);
                    log.info("Default user created with email={} and password={}, please change it", user.getEmail(), user.getPassword());
                }
            }
        };
    }

}
