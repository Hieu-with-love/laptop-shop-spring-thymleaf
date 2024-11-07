package devzeus.com.laptop_shop.dtos.requests;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDTO {
    Long itemId;
    int quantity;
    BigDecimal unitPrice;
    private ProductDTO productDto;
}
