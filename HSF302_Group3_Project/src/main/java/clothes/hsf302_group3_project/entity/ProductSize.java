// ProductSize.java (đổi thành enum hoặc String)
package clothes.hsf302_group3_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_size")
@Getter
@Setter
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, length = 10) // S, M, L, XL
    private String size;

    @Column(nullable = false)
    private Integer quantity;
}