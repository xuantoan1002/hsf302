package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.request.ChangePasswordRequest;
import clothes.hsf302_group3_project.dto.request.GetUserRequest;
import clothes.hsf302_group3_project.dto.response.UserDTO;
import clothes.hsf302_group3_project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/admins")
    public ModelAndView getAllAdmins(@Valid @ModelAttribute GetUserRequest getUserRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/admin");
        Page<UserDTO> admins = userService.getAdmins(getUserRequest, pageable);
        mav.addObject("admins", admins);
        mav.addObject("request", getUserRequest);
        mav.addObject("totalPages", admins.getTotalPages());
        mav.addObject("currentPage", admins.getNumber());
        mav.addObject("hasPrevious", admins.hasPrevious());
        mav.addObject("hasNext", admins.hasNext());
        return mav;
    }

    @GetMapping("/admin/shippers")
    public ModelAndView getAllShippers(@Valid @ModelAttribute GetUserRequest getUserRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/shipper");
        Page<UserDTO> shippers = userService.getShippers(getUserRequest, pageable);
        mav.addObject("shippers", shippers);
        mav.addObject("request", getUserRequest);
        mav.addObject("totalPages", shippers.getTotalPages());
        mav.addObject("currentPage", shippers.getNumber());
        mav.addObject("hasPrevious", shippers.hasPrevious());
        mav.addObject("hasNext", shippers.hasNext());
        return mav;
    }

    @GetMapping("/admin/customers")
    public ModelAndView getAllCustomers(@Valid @ModelAttribute GetUserRequest getUserRequest, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/admin/customer");
        Page<UserDTO> customers = userService.getCustomers(getUserRequest, pageable);
        mav.addObject("customers", customers);
        mav.addObject("request", getUserRequest);
        mav.addObject("totalPages", customers.getTotalPages());
        mav.addObject("currentPage", customers.getNumber());
        mav.addObject("hasPrevious", customers.hasPrevious());
        mav.addObject("hasNext", customers.hasNext());
        return mav;
    }

    @PostMapping("/admin/admins/add")
    public ModelAndView addAdmin(@RequestParam("email") String email) {
        userService.addAdmin(email);
        return new ModelAndView("redirect:/admin/admins");
    }

    @PostMapping("/admin/shippers/add")
    public ModelAndView addShipper(@RequestParam("email") String email) {
        userService.addShipper(email);
        return new ModelAndView("redirect:/admin/shippers");
    }

    @PostMapping("/admin/customers/to-shipper")
    public ModelAndView toShipper(@RequestParam("email") String email) {
        userService.makeUserToShipper(email);
        return new ModelAndView("redirect:/admin/shippers");
    }

    @GetMapping("/change-password")
    public ModelAndView getChangePasswordPage() {
        return new ModelAndView("/user/user/change-password").addObject("changePasswordRequest", new ChangePasswordRequest());
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@Valid @ModelAttribute ChangePasswordRequest changePasswordRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/user/user/change-password");
        }
        userService.changePassword(changePasswordRequest);
        return new ModelAndView("redirect:/");
    }


}
