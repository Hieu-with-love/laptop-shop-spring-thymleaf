package devzeus.com.laptop_shop.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    @NotBlank(message = "Image is required")
    @Size(min = 3, max = 200, message = "Image must be between 3 and 200 characters")
    private String imageUrl;
    private int productId;
}
