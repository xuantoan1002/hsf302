package clothes.hsf302_group3_project.dto.response;
import clothes.hsf302_group3_project.dto.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDTO {
    private long id;
    private long quantity;
    private double price;
    private ProductDTO product;
    private CartDTO cart;
}
