package clothes.hsf302_group3_project.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private Integer categoryId;
    private String status;
    private LocalDateTime createdAt;
    private List<ProductImageDTO> images;
    private List<ProductSizeDTO> sizes;

}