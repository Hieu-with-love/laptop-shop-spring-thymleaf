package devzeus.com.laptop_shop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
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

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 1000000000, message = "Price must be less than or equal to 1 million")
    private BigDecimal price;

    private double discount;

    private String ram;
    private Long quantity;
    private String batteryCapacity;
    private String monitor;
    private String graphicCard;
    private String hardDrive;
    private String screenTechnology;
    private String ports;
    private String cpu;
    private Long categoryId;
    private Long brandId;
    private String thumbnail;

    private MultipartFile fileImage;

}
