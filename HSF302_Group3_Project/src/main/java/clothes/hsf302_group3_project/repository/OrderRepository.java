package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
