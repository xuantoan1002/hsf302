package clothes.hsf302_group3_project.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserRequest {

    private String email;
    private String name;
    private String phone;
    @Pattern(regexp = "ASC|DESC", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid sort order!")
    private String sortOrder;
}
