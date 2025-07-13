package clothes.hsf302_group3_project.dto;

import clothes.hsf302_group3_project.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSizeDTO {
    private Integer id;
    private String size; // hoặc Size enum
    private Integer quantity;
    private Product product;
}


