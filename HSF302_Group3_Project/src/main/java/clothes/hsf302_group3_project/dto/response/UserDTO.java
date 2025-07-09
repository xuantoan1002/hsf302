package clothes.hsf302_group3_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String phone;
    private LocalDateTime createdAt;
    private List<OrderDTO> orders;
    private List<OrderDTO> assignedShipments;
}
