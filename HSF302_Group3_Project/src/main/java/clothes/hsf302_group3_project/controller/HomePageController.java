package clothes.hsf302_group3_project.controller;


import clothes.hsf302_group3_project.dto.CategoryDTO;
import clothes.hsf302_group3_project.dto.ProductDTO;
import clothes.hsf302_group3_project.service.CategoryService;
import clothes.hsf302_group3_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomePageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public String home(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategoriesWithProductCount();
        List<ProductDTO> featuredProducts = productService.getFeaturedProducts();

        model.addAttribute("categories", categories);
        model.addAttribute("featuredProducts", featuredProducts);
        return "HomePage";
    }

//    @GetMapping("/search")
//    public String search(@RequestParam String query, Model model) {
//        List<ProductDTO> searchResults = categoryService.searchProducts(query);
//        model.addAttribute("products", searchResults);
//        model.addAttribute("searchQuery", query);
//        return "search-results";
//    }
}
