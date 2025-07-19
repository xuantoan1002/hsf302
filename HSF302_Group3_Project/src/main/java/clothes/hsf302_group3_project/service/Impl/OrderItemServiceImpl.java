package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.response.OrderItemDTO;
import clothes.hsf302_group3_project.entity.OrderItem;
import clothes.hsf302_group3_project.repository.OrderItemRepository;
import clothes.hsf302_group3_project.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ConverterDTO converterDTO;

    @Override
    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream().map(converterDTO::toOrderItemDTO).toList();
    }
}
