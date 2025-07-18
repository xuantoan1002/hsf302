package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.response.CartDTO;
import clothes.hsf302_group3_project.dto.response.CartItemDTO;
import clothes.hsf302_group3_project.entity.Cart;
import clothes.hsf302_group3_project.entity.CartItem;
import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.repository.CartRepository;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

import static clothes.hsf302_group3_project.security.utils.SecurityUtil.getCurrentUserEmail;

@Controller
public class CartController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ConverterDTO converterDTO;
    private final CartService cartService;

    public CartController(CartRepository cartRepository, UserRepository userRepository, ConverterDTO converterDTO, CartService cartService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.converterDTO = converterDTO;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request) {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email).get();
        Cart cart = cartRepository.findByUser(user);

        CartDTO cartDTO = cart == null ? new CartDTO() : converterDTO.convertToCartDTO(cart);
        List<CartItem> cartItems = cart == null ? new ArrayList<>() : cart.getCartItems();

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            cartItemDTOs.add(converterDTO.convertToCartItemDTO(cartItem));
        }

        cartDTO.setItems(cartItemDTOs); 

        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", cartItemDTOs); // optional nếu không dùng
        model.addAttribute("cart", cartDTO); 

        return "cart";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id){
        long cartItemId = id;
        this.cartService.handleRemoveCartItem(cartItemId);
        return "redirect:/cart";
    }

}
