package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.dto.request.RegisterRequest;
import clothes.hsf302_group3_project.dto.request.VerifyAccountRequest;
import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.entity.VerificationToken;
import clothes.hsf302_group3_project.exception.BusinessException;
import clothes.hsf302_group3_project.exception.ResourceAlreadyExistsException;
import clothes.hsf302_group3_project.exception.ResourceNotFoundException;
import clothes.hsf302_group3_project.exception.UnauthorizedException;
import clothes.hsf302_group3_project.repository.TokenRepository;
import clothes.hsf302_group3_project.repository.UserRepository;
import clothes.hsf302_group3_project.service.AuthService;
import clothes.hsf302_group3_project.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String confirmPassword = registerRequest.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            throw new UnauthorizedException("Passwords do not match!");
        }
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exist!");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .isVerified(false)
                .createdAt(LocalDateTime.now())
                .role("CUSTOMER")
                .mustChangePassword(false)
                .build();

        userRepository.save(user);

        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        VerificationToken vt = VerificationToken
                .builder()
                .token(token)
                .expiryTime(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(vt);

        emailService.sendSimpleEmail(user.getEmail(), "Verify your account", "Your code is: " + token);

    }

    @Transactional
    @Override
    public void verifyAccount(VerifyAccountRequest verifyAccountRequest) {
        String email = verifyAccountRequest.getEmail();
        String code = verifyAccountRequest.getCode();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found!"));

        if (Boolean.TRUE.equals(user.getIsVerified())) {
            throw new BusinessException("Your account has already been verified.");
        }

        VerificationToken token = tokenRepository.findByToken(code)
                .orElseThrow(() -> new ResourceNotFoundException("Verification code not found."));

        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Your verification code has expired.");
        }

        User tokenUser = token.getUser();
        if (tokenUser == null || !tokenUser.getEmail().equals(email)) {
            throw new BusinessException("Verification code does not match this email.");
        }

        user.setIsVerified(true);
        userRepository.save(user);
        tokenRepository.delete(token);
    }

    @Transactional
    @Override
    public void resendCode(String email) {
        User thisUser = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found!"));

        if (thisUser.getIsVerified()) {
            throw new BusinessException("Your account has already been verified!");
        }

        tokenRepository.deleteByUser(thisUser);

        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        VerificationToken vt = VerificationToken
                .builder()
                .token(token)
                .expiryTime(LocalDateTime.now().plusMinutes(15))
                .user(thisUser)
                .build();
        tokenRepository.save(vt);

        emailService.sendSimpleEmail(thisUser.getEmail(), "Verify your account", "Your code is: " + token);
    }

    @Transactional
    @Override
    public void resetPassword(String email) {
        User thisUser = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found!")
        );
        String newPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String encodedPassword = passwordEncoder.encode(newPassword);
        thisUser.setPassword(encodedPassword);
        thisUser.setMustChangePassword(true);
        userRepository.save(thisUser);
        emailService.sendSimpleEmail(thisUser.getEmail(), "Reset your password", "Your new password is: " + newPassword);
    }
}
