package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.response.CartDTO;
import clothes.hsf302_group3_project.dto.response.CartItemDTO;
import clothes.hsf302_group3_project.entity.Cart;
import clothes.hsf302_group3_project.entity.CartItem;
import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.repository.CartItemRepository;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.service.CartItemService;
import clothes.hsf302_group3_project.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static clothes.hsf302_group3_project.security.utils.SecurityUtil.getCurrentUserEmail;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CheckoutController(CartService cartService, CartItemService cartItemService, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/confirm-checkout")
    public String confirmCheckout(@ModelAttribute("cart") CartDTO cart,
                                  @RequestParam("cartDetailIds") List<Long> cartItemIds,
                                  Model model, RedirectAttributes redirectAttributes) {
        // Giả sử bạn có thể lấy userId từ session
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email).get();
        if(user.getName() == null || user.getPhone() == null || user.getAddress() == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng cập nhật thông tin cá nhân trước khi thanh toán.");
            return "redirect:/cart";
        }
        Long userId = userRepository.findByEmail(email).get().getId();

        // Lấy danh sách item gốc từ DB (để có thông tin đầy đủ)
        List<CartItem> selectedCartItems = cartItemService.findByIdsAndUserId(cartItemIds, userId);

        // Map tạm thời từ id -> quantity mới (từ frontend gửi về)
        Map<Long, Long> newQuantityMap = cart.getItems().stream()
                .collect(Collectors.toMap(CartItemDTO::getId, CartItemDTO::getQuantity));

        // Gán lại quantity theo dữ liệu người dùng vừa cập nhật
        for (CartItem item : selectedCartItems) {
            if (newQuantityMap.containsKey(item.getId())) {
                item.setQuantity(newQuantityMap.get(item.getId()));
                this.cartItemRepository.save(item);
            }
        }

        int subtotal = selectedCartItems.stream()
                .mapToInt(item -> (int) (item.getPrice() * item.getQuantity()))
                .sum();

        int shippingFee = 0;
        int total = subtotal + shippingFee;

        model.addAttribute("cartItems", selectedCartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("totalPrice", total);
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("name", user.getName());
        model.addAttribute("address", user.getAddress());

        return "checkout";
    }



}
