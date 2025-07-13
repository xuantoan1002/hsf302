package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.entity.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface OrderService {
    void handlePlaceOrder(User user, HttpSession session, List<Long> cartItemIds);
}
