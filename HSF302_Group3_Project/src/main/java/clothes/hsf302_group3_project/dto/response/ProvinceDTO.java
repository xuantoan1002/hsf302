package clothes.hsf302_group3_project.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvinceDTO {
    private Long id;
    private String code;
    private String name;
    private String type;
}
