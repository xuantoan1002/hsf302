package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.request.GetAdminRequest;
import clothes.hsf302_group3_project.dto.response.UserDTO;
import clothes.hsf302_group3_project.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/admins")
    public ModelAndView getAllAdmins(@Valid @ModelAttribute GetAdminRequest getAdminRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/admin");
        Page<UserDTO> admins = userService.getAdmins(getAdminRequest, pageable);
        mav.addObject("admins", admins);
        mav.addObject("request", getAdminRequest);
        mav.addObject("totalPages", admins.getTotalPages());
        mav.addObject("currentPage", admins.getNumber());
        mav.addObject("hasPrevious", admins.hasPrevious());
        mav.addObject("hasNext", admins.hasNext());
        return mav;
    }

}
