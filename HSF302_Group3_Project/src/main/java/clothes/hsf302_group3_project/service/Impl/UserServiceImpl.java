package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.request.ChangePasswordRequest;
import clothes.hsf302_group3_project.dto.request.GetUserRequest;
import clothes.hsf302_group3_project.dto.response.UserDTO;
import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.exception.BusinessException;
import clothes.hsf302_group3_project.exception.ResourceAlreadyExistsException;
import clothes.hsf302_group3_project.exception.ResourceNotFoundException;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.security.utils.SecurityUtil;
import clothes.hsf302_group3_project.service.EmailService;
import clothes.hsf302_group3_project.service.UserService;
import clothes.hsf302_group3_project.utils.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConverterDTO converterDTO;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public Page<UserDTO> getAdmins(GetUserRequest getUserRequest, Pageable pageable) {
        String email = getUserRequest.getEmail();
        String name = getUserRequest.getName();
        String phone = getUserRequest.getPhone();
        String sortOrder = getUserRequest.getSortOrder();

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

    @Override
    public Page<UserDTO> getCustomers(GetUserRequest getUserRequest, Pageable pageable) {
        String email = getUserRequest.getEmail();
        String name = getUserRequest.getName();
        String phone = getUserRequest.getPhone();
        String sortOrder = getUserRequest.getSortOrder();

        if (sortOrder == null || sortOrder.isBlank()) {
            sortOrder = "DESC";
        }

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                10,
                Sort.by(direction, "createdAt")
        );

        Page<User> customers = userRepository.findCustomers(name, email, phone, pageable);
        return customers.map(converterDTO::convertToUserDTO);
    }

    @Override
    public Page<UserDTO> getShippers(GetUserRequest getUserRequest, Pageable pageable) {
        String email = getUserRequest.getEmail();
        String name = getUserRequest.getName();
        String phone = getUserRequest.getPhone();
        String sortOrder = getUserRequest.getSortOrder();

        if (sortOrder == null || sortOrder.isBlank()) {
            sortOrder = "DESC";
        }

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                10,
                Sort.by(direction, "createdAt")
        );

        Page<User> shippers = userRepository.findShippers(name, email, phone, pageable);
        return shippers.map(converterDTO::convertToUserDTO);
    }

    @Transactional
    @Override
    public void addAdmin(String email) {
        User thisUser = userRepository.findByEmail(email).orElse(null);
        if (thisUser != null) {
            throw new ResourceAlreadyExistsException("Email already exists!");
        }

        String randomPassword = RandomStringUtil.getRandomString(8);
        String encryptedPassword = passwordEncoder.encode(randomPassword);

        User user = User.builder()
                .email(email)
                .password(encryptedPassword)
                .role("ADMIN")
                .createdAt(LocalDateTime.now())
                .isVerified(true)
                .mustChangePassword(true)
                .build();
        userRepository.save(user);

        emailService.sendSimpleEmail(email, "Grant system administrator password", "Your password is: " + randomPassword);

    }

    @Transactional
    @Override
    public void addShipper(String email) {
        User thisUser = userRepository.findByEmail(email).orElse(null);
        if (thisUser != null) {
            throw new ResourceAlreadyExistsException("Email already exists!");
        }

        String randomPassword = RandomStringUtil.getRandomString(8);
        String encryptedPassword = passwordEncoder.encode(randomPassword);
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .email(email)
                .password(encryptedPassword)
                .role("SHIPPER")
                .createdAt(now)
                .toShipperAt(now)
                .isVerified(true)
                .mustChangePassword(true)
                .build();
        userRepository.save(user);

        emailService.sendSimpleEmail(email, "Grant system shipper password", "Your password is: " + randomPassword);

    }

    @Transactional
    @Override
    public void makeUserToShipper(String email) {
        User thisUser = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found!")
        );
        if (!thisUser.getRole().equals("CUSTOMER")) {
            throw new BusinessException("This user can't make a shipper!");
        }
        thisUser.setRole("SHIPPER");
        thisUser.setToShipperAt(LocalDateTime.now());
        userRepository.save(thisUser);

    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = SecurityUtil.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found!")
        );
        String currentPassword = currentUser.getPassword();
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), currentPassword)) {
            throw new BusinessException("Old password does not match!");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            throw new BusinessException("Confirm password does not match!");
        }
        currentUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(currentUser);
    }

}
