package clothes.hsf302_group3_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Double total;
    private String status;
    private LocalDateTime createdAt;
    private UserDTO customer;
    private UserDTO shipper;
    private List<OrderItemDTO> orderItems;
}
