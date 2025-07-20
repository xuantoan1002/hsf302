package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.request.DiscountEventRequest;
import clothes.hsf302_group3_project.dto.response.DiscountEventDTO;
import clothes.hsf302_group3_project.dto.response.ProductDTO;
import clothes.hsf302_group3_project.service.DiscountService;
import clothes.hsf302_group3_project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin/discount")
public class AdminDiscountEventController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ModelAndView showAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DiscountEventDTO> eventsPage = discountService.findAll(pageable);

        ModelAndView modelAndView = new ModelAndView("admin/discount/list");
        modelAndView.addObject("eventsPage", eventsPage);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", eventsPage.getTotalPages());

        return modelAndView;
    }
    @GetMapping("/active")
    public ModelAndView showActiveEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DiscountEventDTO> eventsPage = discountService.getActiveDiscounts(pageable);

        ModelAndView modelAndView = new ModelAndView("admin/discount/active");
        modelAndView.addObject("eventsPage", eventsPage);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", eventsPage.getTotalPages());

        return modelAndView;
    }
    @GetMapping("/inactive")
    public ModelAndView showInactiveEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DiscountEventDTO> eventsPage = discountService.getInactiveDiscounts(pageable);

        ModelAndView modelAndView = new ModelAndView("admin/discount/inactive");
        modelAndView.addObject("eventsPage", eventsPage);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", eventsPage.getTotalPages());

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView mav = new ModelAndView("admin/discount/create-form");
        mav.addObject("products", productService.getAllProducts());
        mav.addObject("request", new DiscountEventRequest());
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        DiscountEventDTO eventDTO = discountService.findById(id);

        DiscountEventRequest request = new DiscountEventRequest();
        request.setName(eventDTO.getName());
        request.setStartDate(eventDTO.getStartDate());
        request.setEndDate(eventDTO.getEndDate());
        request.setDiscountType(eventDTO.getDiscountType());
        request.setDiscountValue(eventDTO.getDiscountValue());
        request.setTargetType(eventDTO.getTargetType());
        request.setProductId(eventDTO.getProductId());
        request.setNote(eventDTO.getNote());

        ModelAndView mav = new ModelAndView("admin/discount/edit-form");
        mav.addObject("request", request);
        mav.addObject("products", productService.getAllProducts());
        mav.addObject("id", id);

        return mav;
    }
    @PostMapping("/create")
    public ModelAndView createDiscount(
            @Valid @ModelAttribute("request") DiscountEventRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("admin/discount/create-form");
            mav.addObject("products", productService.getAllProducts());
            mav.addObject("request", request);
            return mav;
        }

        discountService.create(request);
        return new ModelAndView("redirect:/admin/discount/list");
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateDiscount(
            @PathVariable Long id,
            @Valid @ModelAttribute("request") DiscountEventRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("admin/discount/edit-form");
            mav.addObject("request", request);
            mav.addObject("products", productService.getAllProducts());
            mav.addObject("id", id);
            return mav;
        }

        discountService.update(id, request);
        return new ModelAndView("redirect:/admin/discount/list");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteDiscount(@PathVariable Long id) {
        discountService.delete(id);
        return new ModelAndView("redirect:/admin/discount/list");
    }

}
