package clothes.hsf302_group3_project.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    private String name;
    private List<ProductDTO> products;
    private int productCount;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
