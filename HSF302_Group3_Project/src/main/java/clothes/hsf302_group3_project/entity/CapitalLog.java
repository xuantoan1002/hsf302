package clothes.hsf302_group3_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "capital_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapitalLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = true)
    private Warehouse warehouse;

    @Column(name = "amount_spent", nullable = false)
    private Long amountSpent;

    @Column(name = "purpose", columnDefinition = "nvarchar(max)")
    private String purpose;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

