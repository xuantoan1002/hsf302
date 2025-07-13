package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.ProductImageDTO;

import java.util.List;

public interface ProductImageService {
    List<ProductImageDTO> getAllImages();
    ProductImageDTO getImageById(Integer id);
    void createImage(ProductImageDTO imageDTO);
    void updateImage(Integer id, ProductImageDTO imageDTO);
    void deleteImage(Integer id);
}