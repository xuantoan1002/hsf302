package clothes.hsf302_group3_project.controller;

import clothes.hsf302_group3_project.dto.response.CategoryDTO;
import clothes.hsf302_group3_project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesWithProductCount());
        return "category/category";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "category/create-category"; // Trỏ đến file tạo mới
    }

    @PostMapping
    public String createCategory(@ModelAttribute CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "category/edit-category"; // Trỏ đến file chỉnh sửa
    }

    @PostMapping("/{id}")
    public String updateCategory(@PathVariable Integer id, @ModelAttribute CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return "redirect:/categories";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}