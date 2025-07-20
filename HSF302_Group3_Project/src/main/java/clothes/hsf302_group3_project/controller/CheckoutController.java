package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.response.CartDTO;
import clothes.hsf302_group3_project.dto.response.CartItemDTO;
import clothes.hsf302_group3_project.entity.Cart;
import clothes.hsf302_group3_project.entity.CartItem;
import clothes.hsf302_group3_project.repository.CartItemRepository;
import clothes.hsf302_group3_project.service.CartItemService;
import clothes.hsf302_group3_project.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CartItemRepository cartItemRepository;

    public CheckoutController(CartService cartService, CartItemService cartItemService, CartItemRepository cartItemRepository) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping("/confirm-checkout")
    public String confirmCheckout(@ModelAttribute("cart") CartDTO cart,
                                  @RequestParam("cartDetailIds") List<Long> cartItemIds,
                                  Model model, HttpSession session) {
        // Giả sử bạn có thể lấy userId từ session
        long userId = 1L;

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

        return "checkout";
    }

}
