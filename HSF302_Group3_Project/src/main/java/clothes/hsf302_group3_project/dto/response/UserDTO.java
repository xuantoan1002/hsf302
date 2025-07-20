package clothes.hsf302_group3_project.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String role;
    private String address;
    private String createdAt;
    private String toShipperAt;
    private String isVerified;
}
