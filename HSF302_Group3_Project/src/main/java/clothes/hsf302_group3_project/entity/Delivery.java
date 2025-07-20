package clothes.hsf302_group3_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipper_username")
    private String shipperUsername;

    @Column(name = "order_id")
    private Long orderId;

    // Nếu bạn có entity Order thì có thể dùng:
    // @ManyToOne
    // @JoinColumn(name = "order_id", insertable = false, updatable = false)
    // private Order order;
}
