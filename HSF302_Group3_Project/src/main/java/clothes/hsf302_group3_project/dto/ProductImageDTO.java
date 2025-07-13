package clothes.hsf302_group3_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {
    private Long id;
    private String imageUrl;
    private Integer productId;
}
