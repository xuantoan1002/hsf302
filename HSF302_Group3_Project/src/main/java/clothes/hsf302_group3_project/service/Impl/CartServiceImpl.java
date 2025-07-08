package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.entity.Cart;
import clothes.hsf302_group3_project.entity.CartItem;
import clothes.hsf302_group3_project.repository.CartItemRepository;
import clothes.hsf302_group3_project.repository.CartRepository;
import clothes.hsf302_group3_project.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void handleRemoveCartItem(long id, HttpSession session) {
        Optional<CartItem> cartItemOptional = this.cartItemRepository.findById(id);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            Cart cart = cartItem.getCart();
            // delete cartItem
            this.cartItemRepository.deleteById(id);

            int cartSum = cart.getSum();
            if (cartSum > 1) {
                cart.setSum(cartSum - 1);
                session.setAttribute("sum", cart.getSum());
                this.cartRepository.save(cart);
            } else {
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);

            }
        }
    }
}
