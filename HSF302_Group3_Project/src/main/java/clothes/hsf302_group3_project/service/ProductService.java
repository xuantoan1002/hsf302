package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getFeaturedProducts();
    List<ProductDTO> searchProducts(String query);
}
