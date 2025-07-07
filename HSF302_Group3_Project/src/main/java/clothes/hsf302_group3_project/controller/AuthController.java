package clothes.hsf302_group3_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("user/auth/login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("user/auth/register");
    }

    @GetMapping("/forgot-password")
    public ModelAndView resetPassword() {
        return new ModelAndView("user/auth/forgot-password");
    }
}
