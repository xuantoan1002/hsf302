package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.dto.response.CategoryDTO;
import clothes.hsf302_group3_project.entity.Category;
import clothes.hsf302_group3_project.exception.ResourceNotFoundException;
import clothes.hsf302_group3_project.repository.CategoryRepository;
import clothes.hsf302_group3_project.repository.ProductRepository;
import clothes.hsf302_group3_project.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CategoryDTO> getAllCategoriesWithProductCount() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> result = new ArrayList<>();
        for (Category category : categories) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            if (category.getProducts() != null) {
                dto.setProductCount(category.getProducts().size());
            } else {
                dto.setProductCount(0);
            }

            result.add(dto);
        }

        return result;
    }
    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        Category savedCategory = categoryRepository.save(category);
        return new CategoryDTO(savedCategory.getId(), savedCategory.getName());
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return new CategoryDTO(updatedCategory.getId(), updatedCategory.getName());
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if(category.getProducts().size() <=0) {
            categoryRepository.delete(category);
        }
        else throw new ResourceNotFoundException("Product in category already exists");
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return new CategoryDTO(category.getId(), category.getName());
    }


}