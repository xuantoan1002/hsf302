package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.dto.CategoryDTO;
import clothes.hsf302_group3_project.entity.Category;
import clothes.hsf302_group3_project.repository.CategoryRepository;
import clothes.hsf302_group3_project.repository.ProductRepository;
import clothes.hsf302_group3_project.service.CategoryService;
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


}
