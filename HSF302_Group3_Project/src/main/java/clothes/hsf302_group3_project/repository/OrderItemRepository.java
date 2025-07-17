package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT ot FROM OrderItem ot WHERE ot.order.id = :orderId")
    List<OrderItem> findByOrderId(Long orderId);

}
