package devzeus.com.laptop_shop.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImgUrlRes {
    private String image;
    private boolean isUrlImage;
}
