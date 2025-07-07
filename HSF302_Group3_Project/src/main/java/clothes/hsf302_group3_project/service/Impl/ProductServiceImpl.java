package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.response.ProductDTO;
import clothes.hsf302_group3_project.entity.Product;
import clothes.hsf302_group3_project.repository.ProductRepository;
import clothes.hsf302_group3_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ConverterDTO converterDTO;
    @Override
    public List<ProductDTO> getFeaturedProducts() {
        List<Product> allProducts = productRepository.findAll();

        List<ProductDTO> featuredProducts = new ArrayList<>();

        // Duyệt qua từng sản phẩm (tối đa 10 sản phẩm đầu tiên)
        for (int i = 0; i < allProducts.size() && i < 10; i++) {
            Product product = allProducts.get(i);
            ProductDTO dto = converterDTO.convertToProductDTO(product);
            featuredProducts.add(dto);
        }
        return featuredProducts;
    }


    @Override
    public List<ProductDTO> searchProducts(String query) {
        return List.of();
    }
}
