package clothes.hsf302_group3_project.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderRequest {

    @Pattern(regexp = "^(PENDING|CONFIRMED|SHIPPING|DELIVERED|COMPLETED|CANCELLED|DELIVERY_FAILED)$")
    private String status;
    @Pattern(regexp = "^(ASC|DESC|)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid sort order!")
    private String sortOrder;
    @Pattern(regexp = "^(createdAt|total|)$", message = "Invalid sort field!")
    private String sortField;
    private String totalFrom;
    private String totalTo;
    private String shipperName;
    private String customerName;

}
