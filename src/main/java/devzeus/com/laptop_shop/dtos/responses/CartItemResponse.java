package devzeus.com.laptop_shop.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private int quantity;
    private String productName;
    private String unitPrice;
    private String totalPrice;
    private String images;
}
