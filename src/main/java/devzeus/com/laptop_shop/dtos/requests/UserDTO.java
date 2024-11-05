package devzeus.com.laptop_shop.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be contains character @.\n Ex: example@gmail.com")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private String confirmPassword;
    private String fullName;
    private String address;
    private boolean isActive;
    @NotNull(message = "Enter your day of birth")
    private LocalDate dayOfBirth;
    private String gender;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Long roleId;
}
