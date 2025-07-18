package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.request.GetOrderRequest;
import clothes.hsf302_group3_project.dto.response.OrderDTO;
import clothes.hsf302_group3_project.dto.response.OrderItemDTO;
import clothes.hsf302_group3_project.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    void handlePlaceOrder(List<Long> cartItemIds);

    boolean markOrderAsCompleted(Long orderId);

    Page<OrderDTO> getOrders(GetOrderRequest request, Pageable pageable);

    List<OrderDTO> getOrders();

    OrderDTO getOrder(Long id);

    void confirmOrder(Long id);

    void cancelOrder(Long id);

    void startShipperOrder(Long id);
}
