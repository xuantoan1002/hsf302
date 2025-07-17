package clothes.hsf302_group3_project.dto.response;


import clothes.hsf302_group3_project.enums.WarehouseStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDTO {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime createdAt;
    private WarehouseStatus status;
}
