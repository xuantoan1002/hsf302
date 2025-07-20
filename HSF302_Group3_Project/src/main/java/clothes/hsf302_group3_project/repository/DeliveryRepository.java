package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByShipperUsername(String username);
}
