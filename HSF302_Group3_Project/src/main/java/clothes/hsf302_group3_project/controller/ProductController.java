package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.response.DiscountEventDTO;
import clothes.hsf302_group3_project.dto.response.ProductDTO;
import clothes.hsf302_group3_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private  ProductSizeService productSizeService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private DiscountService discountService;



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
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new ProductDTO());
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
        return "redirect:/products/" + id;
    }


    private void addSelectOptionsToModel(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesWithProductCount());
        model.addAttribute("availableSizes", productSizeService.getAllSizesWithProductInfo());
        model.addAttribute("availableImages", productImageService.getAllImages());
    }
    @GetMapping("/{productId}/discount")
    public DiscountEventDTO getDiscountForCourse(@PathVariable Integer productId) {
        return discountService.getDiscountInfo(productId);
    }
}