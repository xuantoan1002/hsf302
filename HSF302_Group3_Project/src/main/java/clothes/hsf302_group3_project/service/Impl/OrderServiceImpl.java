package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.request.GetOrderRequest;
import clothes.hsf302_group3_project.dto.response.OrderDTO;
import clothes.hsf302_group3_project.entity.*;
import clothes.hsf302_group3_project.enums.OrderStatus;
import clothes.hsf302_group3_project.exception.BusinessException;
import clothes.hsf302_group3_project.repository.CartItemRepository;
import clothes.hsf302_group3_project.repository.CartRepository;
import clothes.hsf302_group3_project.repository.OrderItemRepository;
import clothes.hsf302_group3_project.repository.OrderRepository;
import clothes.hsf302_group3_project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final ConverterDTO converterDTO;

    @Transactional
    public void handlePlaceOrder(
            User user, HttpSession session, List<Long> cartItemIds) {

        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartItem> cartItems = new ArrayList<>();
            for (Long cartItemId : cartItemIds) {
                cartItems.add(cartItemRepository.findById(cartItemId).get());
            }

            if (cartItems != null) {
                //create order
                Order order = new Order();
                order.setCustomer(user);

                order.setStatus(OrderStatus.PAID);

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

                if (cart.getSum() > 0) {
                    this.cartRepository.save(cart);
                } else {
                    cartRepository.deleteCartItemsByCartId(cart.getId());
                }
            }
        }
    }

    @Override
    public Page<OrderDTO> getOrders(GetOrderRequest request, Pageable pageable) {
        String shipperName = request.getShipperName();
        String customerName = request.getCustomerName();
        OrderStatus status = (request.getStatus() == null || request.getStatus().isBlank()) ? OrderStatus.PAID : OrderStatus.valueOf(request.getStatus());
        String sortOrder = (request.getSortOrder() == null || request.getSortOrder().isBlank()) ? "DESC" : request.getSortOrder();
        String sortField = (request.getSortField() == null || request.getSortField().isBlank()) ? "createdAt" : request.getSortField();
        Double totalFrom, totalTo;
        try {
            totalFrom = (request.getTotalFrom() == null || request.getTotalFrom().isBlank()) ? null : Double.parseDouble(request.getTotalFrom());
            totalTo = (request.getTotalTo() == null || request.getTotalTo().isBlank()) ? null : Double.parseDouble(request.getTotalTo());
        } catch (NumberFormatException e) {
            throw new BusinessException("Total value must be a number!");
        }

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        pageable = PageRequest.of(pageable.getPageNumber(), 10, Sort.by(direction, sortField));
        Page<Order> orders;
        if (OrderStatus.PAID.equals(status) || OrderStatus.CANCELLED.equals(status) || OrderStatus.CONFIRMED.equals(status)) {
            orders = orderRepository.findNoneShipperOrders(customerName, status, totalFrom, totalTo, pageable);
        } else {
            orders = orderRepository.findOrders(shipperName, customerName, status, totalFrom, totalTo, pageable);
        }
        return orders.map(converterDTO::convertToOrderDTO);
    }

    @Override
    public void updateStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Order not found with id: " + orderId));
        try {
            OrderStatus status = OrderStatus.valueOf(newStatus.toUpperCase());
            order.setStatus(status);
            orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid order status: " + newStatus);
        }
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Order not found with id: " + orderId));
    }


}
