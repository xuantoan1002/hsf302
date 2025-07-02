package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategoriesWithProductCount();

}
