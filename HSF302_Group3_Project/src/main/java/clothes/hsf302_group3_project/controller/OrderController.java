package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository, UserRepository userRepository1) {
        this.orderService = orderService;
        this.userRepository = userRepository1;
    }

    @GetMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("cartItemIds") List<Long> cartItemIds){
        HttpSession session = request.getSession(false);
        long id = 1l;
//        long id = (long) session.getAttribute("id");
        User currentUser = userRepository.findById(id).get();
        orderService.handlePlaceOrder(currentUser, session, cartItemIds);

        return "redirect:/";
    }
}
