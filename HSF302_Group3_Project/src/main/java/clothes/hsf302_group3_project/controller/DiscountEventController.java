package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.response.DiscountEventDTO;
import clothes.hsf302_group3_project.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin/discount")
public class DiscountEventController {
    @Autowired
    private DiscountService discountService;

    @GetMapping("/list")
    public ModelAndView showAllEvents() {
        List<DiscountEventDTO> discountEventDTOs = discountService.findAll();

        ModelAndView modelAndView = new ModelAndView("admin/discount/list");
        modelAndView.addObject("events", discountEventDTOs);

        return modelAndView;
    }
}
