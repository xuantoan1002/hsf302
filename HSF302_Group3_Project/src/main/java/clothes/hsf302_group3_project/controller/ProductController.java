package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.response.ProductDTO;
import clothes.hsf302_group3_project.dto.response.ProductSizeDTO;
import clothes.hsf302_group3_project.service.CategoryService;
import clothes.hsf302_group3_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private  ProductService productService;
    @Autowired
    private  CategoryService categoryService;

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer categoryId,
            Model model) {

        List<ProductDTO> products;

        if (query != null && !query.isEmpty()) {
            products = productService.searchProducts(query);
        } else if (categoryId != null) {
            products = productService.getProductsByCategory(categoryId);
        } else {
            products = productService.getAllProducts();
        }

        // Add attributes to model
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategoriesWithProductCount());
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("query", query);

        return "product/list";
    }
    // ProductController.java
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        ProductDTO productDTO = new ProductDTO();

        // Initialize with 4 sizes
        List<ProductSizeDTO> sizes = new ArrayList<>();
        for (String sizeName : Arrays.asList("S", "M", "L", "XL")) {
            ProductSizeDTO sizeDTO = new ProductSizeDTO();
            sizeDTO.setSizeName(sizeName);
            sizeDTO.setQuantity(0); // Default quantity
            sizes.add(sizeDTO);
        }
        productDTO.setSizes(sizes);

        model.addAttribute("product", productDTO);
        addSelectOptionsToModel(model);
        return "product/create";
    }

    @PostMapping
    public String createProduct(@ModelAttribute ProductDTO productDTO) {
        productService.createProduct(productDTO);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model) {
        ProductDTO productDTO = productService.getProductById(id);
        model.addAttribute("product", productDTO);
        addSelectOptionsToModel(model);
        return "product/update";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Integer id, @ModelAttribute ProductDTO productDTO) {
        productDTO.setId(id);
        productService.updateProduct(productDTO);
        return "redirect:/products";
    }


    private void addSelectOptionsToModel(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesWithProductCount());
    }
}