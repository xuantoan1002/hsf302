package clothes.hsf302_group3_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "to_shipper_at")
    private LocalDateTime toShipperAt;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "must_change_password")
    private Boolean mustChangePassword;

    @OneToMany(mappedBy = "customer")
    private List<Order> placedOrders;

    @OneToMany(mappedBy = "shipper")
    private List<Order> assignedShipments;

    @OneToMany(mappedBy = "user")
    private List<VerificationToken> tokens;

}

