package clothes.hsf302_group3_project.dto.response;

import lombok.Getter;
import lombok.Setter;

// ProductSizeDTO.java
@Getter
@Setter
public class ProductSizeDTO {
    private Integer id;
    private String sizeName; // Changed from sizeId and sizeName
    private Integer quantity;
    private Integer productId;
}