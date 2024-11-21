package devzeus.com.laptop_shop.dtos.requests;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    private String name;
    private String os;
    private String warranty;
}
