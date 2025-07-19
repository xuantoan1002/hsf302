package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.request.GetOrderRequest;
import clothes.hsf302_group3_project.dto.response.OrderDTO;
import clothes.hsf302_group3_project.dto.response.OrderItemDTO;
import clothes.hsf302_group3_project.entity.*;
import clothes.hsf302_group3_project.enums.OrderStatus;
import clothes.hsf302_group3_project.exception.BusinessException;
import clothes.hsf302_group3_project.repository.*;
import clothes.hsf302_group3_project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static clothes.hsf302_group3_project.security.utils.SecurityUtil.getCurrentUserEmail;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final ConverterDTO converterDTO;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Transactional
    public void handlePlaceOrder(List<Long> cartItemIds) {
        String email = getCurrentUserEmail();
        User user = this.userRepository.findByEmail(email).get();
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
                order.setStatus(OrderStatus.PENDING);

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

    @Transactional
    @Override
    public boolean markOrderAsCompleted(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            return false;
        }

        Order order = orderOpt.get();
        if (order.getStatus() == OrderStatus.COMPLETED) {
            return false;
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        return true;
    }


    @Override
    public Page<OrderDTO> getOrders(GetOrderRequest request, Pageable pageable) {
        String shipperName = request.getShipperName();
        String customerName = request.getCustomerName();
        OrderStatus status = (request.getStatus() == null || request.getStatus().isBlank()) ? OrderStatus.PENDING : OrderStatus.valueOf(request.getStatus());
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
        if (OrderStatus.PENDING.equals(status) || OrderStatus.CANCELLED.equals(status) || OrderStatus.CONFIRMED.equals(status)) {
            orders = orderRepository.findNoneShipperOrders(customerName, status, totalFrom, totalTo, pageable);
        } else {
            orders = orderRepository.findOrders(shipperName, customerName, status, totalFrom, totalTo, pageable);
        }
        return orders.map(converterDTO::convertToOrderDTO);
    }

    @Override
    public List<OrderDTO> getOrders() {
        User user = userRepository.findByEmail(getCurrentUserEmail()).get();
        List<Order> orders = user.getPlacedOrders();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(converterDTO.convertToOrderDTO(order));
        }
        return orderDTOs;
    }

    @Transactional
    @Override
    public void confirmOrder(Long id) {
        Order thisOrder = orderRepository.findById(id).orElseThrow(
                () -> new BusinessException("Order not found!")
        );
        if (!thisOrder.getStatus().equals(OrderStatus.PENDING)) {
            throw new BusinessException("Order status must be PENDING to Confirm!");
        }
        thisOrder.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(thisOrder);

        OrderStatusHistory orderStatusHistory = OrderStatusHistory.builder()
                .order(thisOrder)
                .status(OrderStatus.CONFIRMED)
                .changedAt(LocalDateTime.now())
                .build();

        orderStatusHistoryRepository.save(orderStatusHistory);
    }

    @Transactional
    @Override
    public void cancelOrder(Long id) {
        Order thisOrder = orderRepository.findById(id).orElseThrow(
                () -> new BusinessException("Order not found!")
        );
        if (!thisOrder.getStatus().equals(OrderStatus.PENDING) && !thisOrder.getStatus().equals(OrderStatus.CONFIRMED)) {
            throw new BusinessException("Order status must be PENDING or CONFIRMED to Cancel!");
        }
        thisOrder.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(thisOrder);

        OrderStatusHistory orderStatusHistory = OrderStatusHistory.builder()
                .order(thisOrder)
                .status(OrderStatus.CANCELLED)
                .changedAt(LocalDateTime.now())
                .build();

        orderStatusHistoryRepository.save(orderStatusHistory);
    }

    @Transactional
    @Override
    public void assignShipper(Long orderId, Long shipperId) {
        Order thisOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException("Order not found!")
        );
        if (!thisOrder.getStatus().equals(OrderStatus.CONFIRMED)) {
            throw new BusinessException("Order status must be CONFIRMED to start shipping!");
        }
        User thisShipper = userRepository.findById(shipperId).orElseThrow(
                () -> new BusinessException("Shipper not found!")
        );
        thisOrder.setShipper(thisShipper);
        orderRepository.save(thisOrder);
    }

    @Override
    public Page<OrderDTO> getOrdersByShipperId(Long shipperId, GetOrderRequest getOrderRequest, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new BusinessException("Order not found!")
        );
        return converterDTO.convertToOrderDTO(order);
    }

}
