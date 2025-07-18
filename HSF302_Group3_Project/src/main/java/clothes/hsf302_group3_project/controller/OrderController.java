package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.request.GetOrderRequest;
import clothes.hsf302_group3_project.dto.request.GetUserRequest;
import clothes.hsf302_group3_project.dto.response.OrderDTO;
import clothes.hsf302_group3_project.dto.response.OrderItemDTO;
import clothes.hsf302_group3_project.entity.Order;
import clothes.hsf302_group3_project.entity.OrderItem;
import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.enums.OrderStatus;
import clothes.hsf302_group3_project.repository.OrderItemRepository;
import clothes.hsf302_group3_project.repository.OrderRepository;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.service.OrderItemService;
import clothes.hsf302_group3_project.service.OrderService;
import clothes.hsf302_group3_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static clothes.hsf302_group3_project.security.utils.SecurityUtil.getCurrentUserEmail;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ConverterDTO converterDTO;
    private final OrderItemService orderItemService;
    private final UserService userService;

    @GetMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("cartItemIds") List<Long> cartItemIds,
            @RequestParam("recipientName")String recepientName,
            @RequestParam("recipientPhone")String recepientPhone,
            @RequestParam("recipientAddress")String recepientAddress) {

        String email = getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email).get();
        orderService.handlePlaceOrder(cartItemIds);

        return "redirect:/";
    }

    @GetMapping("/order-history")
    public String getOrders(Model model) {
        List<OrderDTO> orders = orderService.getOrders();
        model.addAttribute("orders", orders);
        return "order-history";
    }

    @PutMapping("/api/orders/{id}/complete")
    public ResponseEntity<?> markOrderAsCompleted(@PathVariable Long id) {
        System.out.println(">>>> Đã vào được markOrderAsCompleted với ID: " + id);
        boolean success = orderService.markOrderAsCompleted(id);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Không thể cập nhật trạng thái đơn hàng.");
        }
    }

    @GetMapping("/order/{orderId}/items")
    public String viewOrderItems(@PathVariable Long orderId, Model model) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        Order o = orderRepository.findById(orderId).get();
        for(OrderItem oi : o.getOrderItems()) {
            orderItems.add(converterDTO.toOrderItemDTO(oi));
        }
        model.addAttribute("orderItems", orderItems);
        return "order-item";
    }


    @GetMapping("/admin/orders")
    public ModelAndView getOrders(@Valid @ModelAttribute GetOrderRequest getOrderRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/order");
        Page<OrderDTO> orders = orderService.getOrders(getOrderRequest, pageable);
        mav.addObject("orders", orders);
        mav.addObject("request", getOrderRequest);
        mav.addObject("totalPages", orders.getTotalPages());
        mav.addObject("currentPage", orders.getNumber());
        mav.addObject("hasPrevious", orders.hasPrevious());
        mav.addObject("hasNext", orders.hasNext());
        return mav;
    }

    @GetMapping("/admin/orders/{id}")
    public ModelAndView getOrder(@PathVariable Long id, @Valid @ModelAttribute GetUserRequest getUserRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/order-detail");
        OrderDTO order = orderService.getOrder(id);
        mav.addObject("order", order);
        mav.addObject("orderItems", orderItemService.getOrderItemsByOrderId(id));
        if (order.getStatus().equals(OrderStatus.CONFIRMED.toString())) {
            mav.addObject("shippers", userService.getAvailableShippers(getUserRequest, pageable));
        }
        return mav;
    }

    @PostMapping("/admin/orders/{id}/confirm")
    public String confirmOrder(@PathVariable Long id) {
        orderService.confirmOrder(id);
        return "redirect:/admin/orders/" + id;
    }

    @PostMapping("/admin/orders/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return "redirect:/admin/orders/" + id;
    }

    @PostMapping("/admin/orders/{id}/start-shipping")
    public String startShippingOrder(@PathVariable Long id) {
        orderService.startShipperOrder(id);
        return "redirect:/admin/orders/" + id;
    }


}
