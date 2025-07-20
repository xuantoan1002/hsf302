package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
