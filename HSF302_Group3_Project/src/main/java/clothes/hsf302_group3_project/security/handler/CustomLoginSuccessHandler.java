package clothes.hsf302_group3_project.security.handler;

import clothes.hsf302_group3_project.security.user.CustomUserDetails;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        if (user.isMustChangePassword()) {
            response.sendRedirect("/myapp/change-password");
        } else {
            if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                response.sendRedirect("/myapp/admin/admins");
            } else {
                response.sendRedirect("/myapp/register");
            }
        }
    }
}
