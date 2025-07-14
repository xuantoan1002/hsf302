package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.ProductImageDTO;
import clothes.hsf302_group3_project.service.ProductImageService;
import clothes.hsf302_group3_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product-images")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;


    @GetMapping
    public String listImages(Model model) {
        model.addAttribute("images", productImageService.getAllImages());
        return "product-img/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("image", new ProductImageDTO());
        model.addAttribute("products", productService.getAllProducts());
        return "product-img/create";
    }

    @PostMapping("/create")
    public String createImage(@ModelAttribute ProductImageDTO imageDTO) {
        productImageService.createImage(imageDTO);
        return "redirect:/product-images";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("image", productImageService.getImageById(id));
        model.addAttribute("products", productService.getAllProducts());
        return "product-img/update";
    }

    @PostMapping("/{id}/update")
    public String updateImage(@PathVariable Integer id, @ModelAttribute ProductImageDTO imageDTO) {
        productImageService.updateImage(id, imageDTO);
        return "redirect:/product-images";
    }

    @GetMapping("/{id}/delete")
    public String deleteImage(@PathVariable Integer id) {
        productImageService.deleteImage(id);
        return "redirect:/product-images";
    }
}