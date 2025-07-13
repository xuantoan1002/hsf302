package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.ProductSizeDTO;

import java.util.List;

public interface ProductSizeService {
    List<ProductSizeDTO> getAllSizesWithProductInfo();

    void createSize(ProductSizeDTO sizeDTO);
    void updateSize(Integer id, ProductSizeDTO sizeDTO);
    ProductSizeDTO getSizeById(Integer id);
    // Các phương thức khác giữ nguyên
}