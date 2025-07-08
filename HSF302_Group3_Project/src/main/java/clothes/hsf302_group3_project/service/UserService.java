package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.request.GetAdminRequest;
import clothes.hsf302_group3_project.dto.response.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> getAdmins(GetAdminRequest getAdminRequest, Pageable pageable);

}
