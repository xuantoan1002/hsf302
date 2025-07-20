package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.request.ChangePasswordRequest;
import clothes.hsf302_group3_project.dto.request.GetUserRequest;
import clothes.hsf302_group3_project.dto.response.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> getAdmins(GetUserRequest getUserRequest, Pageable pageable);

    Page<UserDTO> getCustomers(GetUserRequest getUserRequest, Pageable pageable);

    Page<UserDTO> getShippers(GetUserRequest getUserRequest, Pageable pageable);

    void addAdmin(String email);

    void addShipper(String email);

    void makeUserToShipper(String email);

    void changePassword (ChangePasswordRequest changePasswordRequest);
}
