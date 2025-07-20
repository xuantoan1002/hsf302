package clothes.hsf302_group3_project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message = "Password is required!")
    private String oldPassword;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must be at least 8 characters!")
    private String newPassword;

    @NotBlank(message = "Confirm password is required!")
    private String confirmNewPassword;

}
