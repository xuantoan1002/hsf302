package clothes.hsf302_group3_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WardDTO {
    private Long id;
    private String code;
    private String name;
    private String type;
}
