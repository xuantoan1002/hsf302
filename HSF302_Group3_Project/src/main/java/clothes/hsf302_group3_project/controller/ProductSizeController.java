package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.response.ProductSizeDTO;
import clothes.hsf302_group3_project.enums.Size;
import clothes.hsf302_group3_project.service.ProductService;
import clothes.hsf302_group3_project.service.ProductSizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product-sizes")
public class ProductSizeController {

    private final ProductSizeService productSizeService;
    private final ProductService productService;

    public ProductSizeController(ProductSizeService productSizeService,
                                 ProductService productService) {
        this.productSizeService = productSizeService;
        this.productService = productService;
    }


    @GetMapping
    public String listSizes(Model model) {
        model.addAttribute("sizes", productSizeService.getAllSizesWithProductInfo());
        return "product-size/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        ProductSizeDTO sizeDTO = new ProductSizeDTO();
        model.addAttribute("size", sizeDTO);
        model.addAttribute("allSizes", Size.values());
        model.addAttribute("products", productService.getAllProducts());
        return "product-size/create";
    }

    @PostMapping("/create")
    public String createSize(@ModelAttribute ProductSizeDTO sizeDTO) {
        productSizeService.createSize(sizeDTO);
        return "redirect:/product-sizes";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("size", productSizeService.getSizeById(id));
        model.addAttribute("allSizes", Size.values());
        model.addAttribute("products", productService.getAllProducts());
        return "product-size/update";
    }

    @PostMapping("/{id}/update")
    public String updateSize(@PathVariable Integer id, @ModelAttribute ProductSizeDTO sizeDTO) {
        productSizeService.updateSize(id, sizeDTO);
        return "redirect:/product-sizes";
    }

    // Các phương thức khác (list, delete) giữ nguyên
}