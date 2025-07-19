package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.request.ChangePasswordRequest;
import clothes.hsf302_group3_project.dto.request.GetOrderRequest;
import clothes.hsf302_group3_project.dto.request.GetUserRequest;
import clothes.hsf302_group3_project.dto.response.OrderDTO;
import clothes.hsf302_group3_project.dto.response.UserDTO;
import clothes.hsf302_group3_project.service.OrderService;
import clothes.hsf302_group3_project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/admin/admins")
    public ModelAndView getAllAdmins(@Valid @ModelAttribute GetUserRequest getUserRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/admin");
        Page<UserDTO> admins = userService.getAdmins(getUserRequest, pageable);
        mav.addObject("admins", admins);
        mav.addObject("request", getUserRequest);
        mav.addObject("totalPages", admins.getTotalPages());
        mav.addObject("currentPage", admins.getNumber());
        mav.addObject("hasPrevious", admins.hasPrevious());
        mav.addObject("hasNext", admins.hasNext());
        return mav;
    }

    @GetMapping("/admin/shippers")
    public ModelAndView getAllShippers(@Valid @ModelAttribute GetUserRequest getUserRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/shipper");
        Page<UserDTO> shippers = userService.getShippers(getUserRequest, pageable);
        mav.addObject("shippers", shippers);
        mav.addObject("request", getUserRequest);
        mav.addObject("totalPages", shippers.getTotalPages());
        mav.addObject("currentPage", shippers.getNumber());
        mav.addObject("hasPrevious", shippers.hasPrevious());
        mav.addObject("hasNext", shippers.hasNext());
        return mav;
    }

    @GetMapping("/admin/customers")
    public ModelAndView getAllCustomers(@Valid @ModelAttribute GetUserRequest getUserRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/customer");
        Page<UserDTO> customers = userService.getCustomers(getUserRequest, pageable);
        mav.addObject("customers", customers);
        mav.addObject("request", getUserRequest);
        mav.addObject("totalPages", customers.getTotalPages());
        mav.addObject("currentPage", customers.getNumber());
        mav.addObject("hasPrevious", customers.hasPrevious());
        mav.addObject("hasNext", customers.hasNext());
        return mav;
    }

    @PostMapping("/admin/admins/add")
    public ModelAndView addAdmin(@RequestParam("email") String email) {
        userService.addAdmin(email);
        return new ModelAndView("redirect:/admin/admins");
    }

    @PostMapping("/admin/shippers/add")
    public ModelAndView addShipper(@RequestParam("email") String email) {
        userService.addShipper(email);
        return new ModelAndView("redirect:/admin/shippers");
    }

    @PostMapping("/admin/customers/to-shipper")
    public ModelAndView toShipper(@RequestParam("email") String email) {
        userService.makeUserToShipper(email);
        return new ModelAndView("redirect:/admin/shippers");
    }

    @GetMapping("/change-password")
    public ModelAndView getChangePasswordPage() {
        return new ModelAndView("/user/user/change-password").addObject("changePasswordRequest", new ChangePasswordRequest());
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@Valid @ModelAttribute ChangePasswordRequest changePasswordRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/user/user/change-password");
        }
        userService.changePassword(changePasswordRequest);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/admin/shippers/{id}/start-shipping")
    public ModelAndView startShipping(@PathVariable Long id) {
        userService.startShipping(id);
        return new ModelAndView("redirect:/admin/shippers/" + id);
    }

    // cần sửa lọc các shipper available or not
    @GetMapping("/admin/shippers/{id}")
    public ModelAndView getShipperDetailPage(@PathVariable Long id, @Valid @ModelAttribute GetOrderRequest getOrderRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/shipper-detail");
        Page<OrderDTO> orders = orderService.getOrdersByShipperId(id, getOrderRequest, pageable);
        mav.addObject("shipper", userService.getUserById(id));
        mav.addObject("orders", orders);
        mav.addObject("availableOrders", orderService.getAvailableOrders());
        mav.addObject("request", getOrderRequest);
        mav.addObject("totalPages", orders.getTotalPages());
        mav.addObject("currentPage", orders.getNumber());
        mav.addObject("hasPrevious", orders.hasPrevious());
        mav.addObject("hasNext", orders.hasNext());
        return mav;
    }

    @PostMapping("/admin/shippers/{id}/delete") //to customer
    public ModelAndView deleteShipper(@PathVariable Long id) {
        userService.deleteShipper(id);
        return new ModelAndView("redirect:/admin/shippers");
    }

    @PostMapping("/admin/shippers/{shipperId}/add-orders")
    public ModelAndView addOrdersForShipper(@PathVariable Long shipperId, @RequestParam List<Long> orderIds) {
        orderService.addOrdersForShipper(shipperId, orderIds);
        return new ModelAndView("redirect:/admin/shippers/" + shipperId);
    }

    @GetMapping("/admin/customers/{id}")
    public ModelAndView getCustomerDetailPage(@PathVariable Long id, @Valid @ModelAttribute GetOrderRequest getOrderRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/customer-detail");
        Page<OrderDTO> orders = orderService.getOrdersByCustomerId(id, getOrderRequest, pageable);
        mav.addObject("customer", userService.getUserById(id));
        mav.addObject("orders", orders);
        mav.addObject("request", getOrderRequest);
        mav.addObject("totalPages", orders.getTotalPages());
        mav.addObject("currentPage", orders.getNumber());
        mav.addObject("hasPrevious", orders.hasPrevious());
        mav.addObject("hasNext", orders.hasNext());
        return mav;
    }


}
