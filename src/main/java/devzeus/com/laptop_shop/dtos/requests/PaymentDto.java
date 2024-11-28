package devzeus.com.laptop_shop.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentDto {
    private String status;
    private String message;
    private String URL;
}
