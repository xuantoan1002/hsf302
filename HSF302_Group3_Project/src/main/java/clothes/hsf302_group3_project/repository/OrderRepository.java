package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.dto.request.GetOrderRequest;
import clothes.hsf302_group3_project.entity.Order;
import clothes.hsf302_group3_project.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE " +
            "(:shipperName IS NULL OR o.shipper IS NULL OR o.shipper.name LIKE CONCAT('%', :shipperName, '%')) " +
            "AND (:customerName IS NULL OR o.customer.name LIKE CONCAT('%', :customerName, '%')) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:totalFrom IS NULL OR o.total >= :totalFrom) " +
            "AND (:totalTo IS NULL OR o.total <= :totalTo)")
    Page<Order> findOrders(String shipperName, String customerName, OrderStatus status, Double totalFrom, Double totalTo, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE " +
            "(:customerName IS NULL OR o.customer.name LIKE CONCAT('%', :customerName, '%')) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:totalFrom IS NULL OR o.total >= :totalFrom) " +
            "AND (:totalTo IS NULL OR o.total <= :totalTo)")
    Page<Order> findNoneShipperOrders(String customerName, OrderStatus status, Double totalFrom, Double totalTo, Pageable pageable);

    @Modifying
    @Query("UPDATE Order o " +
            "SET o.status = :newStatus " +
            "WHERE o.shipper.id = :shipperId " +
            "AND o.status = :oldStatus")
    void startShippingOrders(Long shipperId, OrderStatus oldStatus, OrderStatus newStatus);

    @Query("SELECT o FROM Order o WHERE o.shipper.id = :shipperId " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:customerName IS NULL OR o.customer.name LIKE CONCAT('%', :customerName, '%')) " +
            "AND (:totalFrom IS NULL OR o.total >= :totalFrom) " +
            "AND (:totalTo IS NULL OR o.total <= :totalTo)")
    Page<Order> findByShipperId(Long shipperId, OrderStatus status, String customerName, Double totalFrom, Double totalTo, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:shipperName IS NULL OR o.shipper.name LIKE CONCAT('%', :shipperName, '%')) " +
            "AND (:totalFrom IS NULL OR o.total >= :totalFrom) " +
            "AND (:totalTo IS NULL OR o.total <= :totalTo)")
    Page<Order> findByCustomerId(Long customerId, OrderStatus status, String shipperName, Double totalFrom, Double totalTo, Pageable pageable);

    @Modifying
    @Query("UPDATE Order o " +
            "SET o.shipper.id = :shipperId " +
            "WHERE o.id IN :orderIds")
    void addOrdersForShipper(Long shipperId, List<Long> orderIds);

    @Query("SELECT o FROM Order o WHERE o.shipper IS NULL AND o.status = :status")
    List<Order> findAvailableOrders(OrderStatus status);

}
