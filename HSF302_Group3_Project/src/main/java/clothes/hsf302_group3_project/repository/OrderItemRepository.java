package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // THÊM DÒNG NÀY
    List<OrderItem> findByOrderId(Long orderId);

}
