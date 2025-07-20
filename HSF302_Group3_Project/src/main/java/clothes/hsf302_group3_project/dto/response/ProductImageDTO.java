package clothes.hsf302_group3_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImageDTO {
    private Long id;
    private String imageUrl;
    private Integer productId;
}