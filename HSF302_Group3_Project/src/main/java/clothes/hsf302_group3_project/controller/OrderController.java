package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.request.GetOrderRequest;
import clothes.hsf302_group3_project.dto.request.GetUserRequest;
import clothes.hsf302_group3_project.dto.response.OrderDTO;
import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.enums.OrderStatus;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.service.OrderItemService;
import clothes.hsf302_group3_project.service.OrderService;
import clothes.hsf302_group3_project.service.UserService;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("cartItemIds") List<Long> cartItemIds) {
        HttpSession session = request.getSession(false);
        long id = 1l;
//        long id = (long) session.getAttribute("id");
        User currentUser = userRepository.findById(id).get();
        orderService.handlePlaceOrder(currentUser, session, cartItemIds);

        return "redirect:/";
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
