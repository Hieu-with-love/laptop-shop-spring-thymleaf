package devzeus.com.laptop_shop.dtos.requests;

import jakarta.validation.constraints.NotBlank;
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

    private String fullName;
    private String address;
    private String password;

    private LocalDate dayOfBirth;

    private Long roleId;
}
