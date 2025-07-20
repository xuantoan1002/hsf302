package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.request.GetOrderRequest;
import clothes.hsf302_group3_project.dto.response.OrderDTO;
import clothes.hsf302_group3_project.entity.Order;
import clothes.hsf302_group3_project.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    void handlePlaceOrder(User user, HttpSession session, List<Long> cartItemIds);

    Page<OrderDTO> getOrders(GetOrderRequest request, Pageable pageable);

    void updateStatus(Long orderId, String newStatus);
    Order findById(Long orderId);
}
