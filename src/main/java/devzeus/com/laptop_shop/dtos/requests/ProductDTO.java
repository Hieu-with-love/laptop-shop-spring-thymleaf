package devzeus.com.laptop_shop.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String name;

    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String description;

    @Min(value = 1, message = "Price must be greater than or equal to 1")
    @Max(value = 1000000000, message = "Price must be less than or equal to 1 million")
    private BigDecimal price;

    @Min(value = 1, message = "Discount must be greater than or equal to 1")
    private double discount;

    private String ram;
    @Min(value = 1, message = "The number of item can't less 1")
    private Long quantity;
    private String batteryCapacity;
    private String monitor;
    private String graphicCard;
    private String hardDrive;
    private String screenTechnology;
    private String ports;
    private String cpu;
    @NotNull(message = "Category is obligated")
    private Long categoryId;
    @NotNull(message = "Category is obligated")
    private Long brandId;
    private String thumbnail;

    private MultipartFile fileImage;

}