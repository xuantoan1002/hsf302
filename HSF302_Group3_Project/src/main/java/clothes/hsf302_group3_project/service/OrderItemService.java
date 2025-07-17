package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.response.OrderItemDTO;
import clothes.hsf302_group3_project.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItemDTO> getOrderItemsByOrderId(Long orderId);
}
