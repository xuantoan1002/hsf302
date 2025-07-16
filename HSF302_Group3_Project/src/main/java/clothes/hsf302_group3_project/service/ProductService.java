package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.response.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Integer id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProduct(Integer id);

    List<ProductDTO> getFeaturedProducts();
    List<ProductDTO> searchProducts(String query);
    List<ProductDTO> getProductsByCategory(Integer categoryId);
    List<ProductDTO> getProductsByCategoryAndSort(Integer categoryId, String sortBy, String direction);
    List<ProductDTO> getAllProductsSorted(String sortBy, String direction);
    List<ProductDTO> sortProducts(List<ProductDTO> products, String sortBy, String direction);
    ProductDTO getProductDetail(Integer productId);

}