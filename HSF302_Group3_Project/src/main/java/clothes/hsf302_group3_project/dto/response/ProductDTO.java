package clothes.hsf302_group3_project.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long categoryId;
    private String status;
    private LocalDateTime createdAt;
    private List<ProductImageDTO> images;

}