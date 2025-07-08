package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.request.GetAdminRequest;
import clothes.hsf302_group3_project.dto.response.UserDTO;
import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConverterDTO converterDTO;

    @Override
    public Page<UserDTO> getAdmins(GetAdminRequest getAdminRequest, Pageable pageable) {
        String email = getAdminRequest.getEmail();
        String name = getAdminRequest.getName();
        String phone = getAdminRequest.getPhone();
        String sortOrder = getAdminRequest.getSortOrder();

        if (sortOrder == null || sortOrder.isBlank()) {
            sortOrder = "DESC";
        }

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                10,
                Sort.by(direction, "createdAt")
        );

        Page<User> admins = userRepository.findAdmins(name, email, phone, pageable);
        return admins.map(converterDTO::convertToUserDTO);
    }
}
