package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.entity.*;
import clothes.hsf302_group3_project.repository.CartItemRepository;
import clothes.hsf302_group3_project.repository.CartRepository;
import clothes.hsf302_group3_project.repository.OrderItemRepository;
import clothes.hsf302_group3_project.repository.OrderRepository;
import clothes.hsf302_group3_project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;

    public OrderServiceImpl(CartRepository cartRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void handlePlaceOrder(
            User user, HttpSession session, List<Long> cartItemIds) {

        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartItem> cartItems = new ArrayList<>();
            for(Long cartItemId : cartItemIds) {
                cartItems.add(cartItemRepository.findById(cartItemId).get());
            }

            if (cartItems != null) {
                //create order
                Order order = new Order();
                order.setCustomer(user);

                order.setStatus("PENDING");

                double sum = 0;
                for (CartItem ct : cartItems) {
                    sum += ct.getQuantity() * ct.getPrice();
                }
                order.setTotal(sum);
                order = this.orderRepository.save(order);

                // create orderItem
                for (CartItem ct : cartItems) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(ct.getProduct());
                    orderItem.setPrice(ct.getPrice());
                    orderItem.setQuantity((int) ct.getQuantity());

                    this.orderItemRepository.save(orderItem);
                }

                for (CartItem cd : cartItems) {
                    this.cartItemRepository.deleteById(cd.getId());
                    cart.setSum(cart.getSum() - 1);
                }

                if(cart.getSum() > 0){
                    this.cartRepository.save(cart);
                }
                else{
                    cartRepository.deleteCartItemsByCartId(cart.getId());
                }

            }
        }

    }

}
