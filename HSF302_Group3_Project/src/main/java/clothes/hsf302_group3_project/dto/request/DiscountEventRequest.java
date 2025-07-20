package clothes.hsf302_group3_project.dto.request;

import clothes.hsf302_group3_project.enums.DiscountType;
import clothes.hsf302_group3_project.enums.TargetType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountEventRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @DecimalMin(value = "0.0", inclusive = false, message = "Discount value must be greater than 0")
    private double discountValue;

    private TargetType targetType = TargetType.ALL;

    private Integer productId;

    private String note;
}