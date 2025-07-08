package clothes.hsf302_group3_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {
    private Long id;
    private int quantity;
    private double price;
    private OrderDTO order;
    private ProductDTO product;
}
