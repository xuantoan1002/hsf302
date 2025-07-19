package clothes.hsf302_group3_project.entity;

import clothes.hsf302_group3_project.enums.DiscountType;
import clothes.hsf302_group3_project.enums.TargetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "discount_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;


    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "discount_type", columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "discount_value")
    private double discountValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    private TargetType targetType;
    private String note;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;
}
