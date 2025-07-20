package clothes.hsf302_group3_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_size2")
@Getter
@Setter
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, columnDefinition = "NVARCHAR(10)")
    private String name; // ví dụ: "S", "M", "L", "XL"

    @Column(nullable = false)
    private Integer quantity;
}