package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.request.RegisterRequest;
import clothes.hsf302_group3_project.dto.request.VerifyAccountRequest;
import clothes.hsf302_group3_project.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        return new ModelAndView("/user/auth/login");
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        return new ModelAndView("/user/auth/register")
                .addObject("registerRequest", new RegisterRequest());
    }

    @GetMapping("/forgot-password")
    public ModelAndView getResetPasswordPage() {
        return new ModelAndView("/user/auth/forgot-password");
    }

    @GetMapping("/verify-account")
    public ModelAndView getVerifyAccountPage(@RequestParam(value = "email", required = false) String email) {
        VerifyAccountRequest verifyRequest = new VerifyAccountRequest();
        verifyRequest.setEmail(email);

        ModelAndView mav = new ModelAndView("/user/auth/verify-account");
        mav.addObject("verifyAccountRequest", verifyRequest);
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/user/auth/register");
        }
        authService.register(registerRequest);
        return new ModelAndView("redirect:/verify-account?email=" + registerRequest.getEmail());
    }

    @PostMapping("/resend-code")
    public ModelAndView resendCode(@RequestParam(value = "email", required = false) String email) {
        authService.resendCode(email);
        return new ModelAndView("redirect:/verify-account?email=" + email);
    }

    @PostMapping("/verify-account")
    public ModelAndView verifyAccount(@Valid @ModelAttribute VerifyAccountRequest verifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/user/auth/verify-account");
        }
        authService.verifyAccount(verifyRequest);
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/forgot-password")
    public ModelAndView resetPassword(@RequestParam(value = "email", required = false) String email) {
        authService.resetPassword(email);
        return new ModelAndView("redirect:/login");
    }
}
