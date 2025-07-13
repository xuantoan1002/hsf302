package clothes.hsf302_group3_project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
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

