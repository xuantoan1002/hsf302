package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.ProductDTO;
import clothes.hsf302_group3_project.dto.ProductImageDTO;
import clothes.hsf302_group3_project.dto.ProductSizeDTO;
import clothes.hsf302_group3_project.entity.Product;
import clothes.hsf302_group3_project.entity.ProductImage;
import clothes.hsf302_group3_project.entity.ProductSize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        dto.setCreatedAt(product.getCreatedAt());

        if (product.getImages() != null) {
            List<ProductImageDTO> imageDTOs = product.getImages().stream()
                    .map(this::convertToProductImageDTO)
                    .collect(Collectors.toList());
            dto.setImages(imageDTOs);
        }

        return dto;
    }

    public ProductImageDTO convertToProductImageDTO(ProductImage image) {
        ProductImageDTO dto = new ProductImageDTO();
        dto.setId(image.getId());
        dto.setImageUrl(image.getImageUrl());
        dto.setProductId(image.getProduct() != null ? image.getProduct().getId() : null);
        return dto;
    }

    public ProductSizeDTO convertToProductSizeDTO(ProductSize productSize) {
        ProductSizeDTO dto = new ProductSizeDTO();
        dto.setId(productSize.getId());
        dto.setSize(productSize.getSize());
        dto.setQuantity(productSize.getQuantity());
        return dto;
    }
}
