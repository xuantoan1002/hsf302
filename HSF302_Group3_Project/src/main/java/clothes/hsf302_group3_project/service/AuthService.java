package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.request.RegisterRequest;
import clothes.hsf302_group3_project.dto.request.VerifyAccountRequest;

public interface AuthService {

    void register(RegisterRequest registerRequest);

    void verifyAccount(VerifyAccountRequest verifyAccountRequest);

    void resendCode(String email);
}
