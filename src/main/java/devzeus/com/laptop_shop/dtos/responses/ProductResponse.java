package devzeus.com.laptop_shop.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String price;
    private String oldPrice;
    private Long quantity;
    private String codeDiscount;
    private String description;
    private String thumbnail;
    private String categoryName;
    private String brandName;
    private boolean isUrlImage;

}
