package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.ProductSizeDTO;
import clothes.hsf302_group3_project.entity.Product;
import clothes.hsf302_group3_project.entity.ProductSize;
import clothes.hsf302_group3_project.exception.ResourceNotFoundException;
import clothes.hsf302_group3_project.repository.ProductSizeRepository;
import clothes.hsf302_group3_project.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ConverterDTO converterDTO;

    @Override
    public List<ProductSizeDTO> getAllSizesWithProductInfo() {
        return productSizeRepository.findAllWithProductInfo().stream()
                .map(size -> {
                    ProductSizeDTO dto = converterDTO.convertToProductSizeDTO(size);
                    if (size.getProduct() != null) {
                        dto.setProduct(size.getProduct());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createSize(ProductSizeDTO sizeDTO) {
        ProductSize size = new ProductSize();

        size.setQuantity(sizeDTO.getQuantity());
        try{
            if (sizeDTO.getProduct().getId() != null && !size.getSize().equals(sizeDTO.getSize())) {
                Product product = new Product();
                product.setId(sizeDTO.getProduct().getId() );
                size.setSize(sizeDTO.getSize());
                size.setProduct(product);
            }
        }catch (ResourceNotFoundException e){
        }

        productSizeRepository.save(size);
    }

    @Override
    @Transactional
    public void updateSize(Integer id, ProductSizeDTO sizeDTO) {
        ProductSize size = productSizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found with id: " + id));

        size.setSize(sizeDTO.getSize());
        size.setQuantity(sizeDTO.getQuantity());

        if (sizeDTO.getProduct().getId()  != null) {
            Product product = new Product();
            product.setId(sizeDTO.getProduct().getId() );
            size.setProduct(product);
        } else {
            size.setProduct(null);
        }

        productSizeRepository.save(size);
    }

    @Override
    public ProductSizeDTO getSizeById(Integer id) {
        ProductSize size = productSizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found with id: " + id));
        return converterDTO.convertToProductSizeDTO(size);
    }

    // Các phương thức khác giữ nguyên
}