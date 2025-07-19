// ProductDTO.java
package clothes.hsf302_group3_project.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String imageUrl;
    private List<ProductSizeDTO> sizes = new ArrayList<>(); // Add this line
}