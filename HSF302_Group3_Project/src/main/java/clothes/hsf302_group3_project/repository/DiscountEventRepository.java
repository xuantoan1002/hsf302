package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.DiscountEvent;
import clothes.hsf302_group3_project.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DiscountEventRepository extends JpaRepository<DiscountEvent, Long> {
    List<DiscountEvent> findByProduct_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Integer productId, LocalDate startDate, LocalDate endDate
    );

    List<DiscountEvent> findByTargetTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            TargetType targetType,
            LocalDate startDate, LocalDate endDate
    );


    List<DiscountEvent> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate now1, LocalDate now2);

    @Query("SELECT d FROM DiscountEvent d LEFT JOIN FETCH d.product")
    List<DiscountEvent> findAllWithProduct();
}
