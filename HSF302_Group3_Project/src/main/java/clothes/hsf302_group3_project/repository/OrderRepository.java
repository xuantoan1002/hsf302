package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.Order;
import clothes.hsf302_group3_project.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

}
