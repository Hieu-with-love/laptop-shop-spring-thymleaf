package devzeus.com.laptop_shop.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistItemResponse {
    private String thumbnail;
    private String title;
    private String unitPrice;
    private boolean status;
    private Long productId;
    private boolean isUrlImg;
}
