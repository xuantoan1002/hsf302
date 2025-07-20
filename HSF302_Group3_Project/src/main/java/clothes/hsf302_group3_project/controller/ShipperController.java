package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.entity.Delivery;
import clothes.hsf302_group3_project.entity.Order;
import clothes.hsf302_group3_project.entity.OrderStatusHistory;
import clothes.hsf302_group3_project.enums.OrderStatus;
import clothes.hsf302_group3_project.service.DeliveryService;
import clothes.hsf302_group3_project.service.OrderService;
import clothes.hsf302_group3_project.service.OrderStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusHistoryService orderStatusHistoryService;

    // Hiển thị danh sách đơn hàng của shipper đang đăng nhập
    @GetMapping
    public String viewShipperOrders(Model model, Principal principal) {
        String shipperUsername = principal.getName(); // lấy username shipper từ session
        List<Delivery> deliveries = deliveryService.getDeliveriesByShipperUsername(shipperUsername);
        model.addAttribute("deliveries", deliveries);
        return "shipper"; // => trả về /myapp/shipper.html qua Thymeleaf
    }

    // Cập nhật trạng thái đơn hàng
    @PostMapping("/orders/update")
    public String updateOrderStatus(@RequestParam Long orderId,
                                    @RequestParam String newStatus) {
        // Cập nhật trạng thái đơn hàng
        orderService.updateStatus(orderId, newStatus);

        // Ghi vào lịch sử trạng thái
        Order order = orderService.findById(orderId); // phải có method findById trong OrderService
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(OrderStatus.valueOf(newStatus));
        history.setChangedAt(LocalDateTime.now());
        orderStatusHistoryService.save(history);
        return "shipper"; // tìm shipper.html trong templates

    }
}
