package clothes.hsf302_group3_project.dto.response;

import clothes.hsf302_group3_project.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountEventDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private DiscountType discountType;
    private double discountValue;
    private Integer productId;
    private String productName;
    private String note;
}
