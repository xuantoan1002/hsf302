package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.ProductDTO;
import clothes.hsf302_group3_project.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ConverterDTO {
    public ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setStatus(product.getStatus());
        return dto;
    }
}
