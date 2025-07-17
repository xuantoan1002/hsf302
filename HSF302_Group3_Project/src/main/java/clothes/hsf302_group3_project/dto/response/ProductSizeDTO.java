package clothes.hsf302_group3_project.dto.response;

import clothes.hsf302_group3_project.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSizeDTO {
    private Integer id;
    private String size;
    private Integer quantity;
    private Product product;
}


