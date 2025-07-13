package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.ProductImageDTO;
import clothes.hsf302_group3_project.entity.Product;
import clothes.hsf302_group3_project.entity.ProductImage;
import clothes.hsf302_group3_project.exception.ResourceNotFoundException;
import clothes.hsf302_group3_project.repository.ProductImageRepository;
import clothes.hsf302_group3_project.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ConverterDTO converterDTO;

    @Override
    public List<ProductImageDTO> getAllImages() {
        return productImageRepository.findAll().stream()
                .map(converterDTO::convertToProductImageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductImageDTO getImageById(Integer id) {
        ProductImage image = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
        return converterDTO.convertToProductImageDTO(image);
    }

    @Override
    @Transactional
    public void createImage(ProductImageDTO imageDTO) {
        ProductImage image = new ProductImage();
        image.setImageUrl(imageDTO.getImageUrl());

        if (imageDTO.getProductId() != null) {
            Product product = new Product();
            product.setId(imageDTO.getProductId());
            image.setProduct(product);
        }

        productImageRepository.save(image);
    }

    @Override
    @Transactional
    public void updateImage(Integer id, ProductImageDTO imageDTO) {
        ProductImage image = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));

        image.setImageUrl(imageDTO.getImageUrl());

        if (imageDTO.getProductId() != null) {
            Product product = new Product();
            product.setId(imageDTO.getProductId());
            image.setProduct(product);
        } else {
            image.setProduct(null);
        }

        productImageRepository.save(image);
    }

    @Override
    @Transactional
    public void deleteImage(Integer id) {
        ProductImage image = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
        productImageRepository.delete(image);
    }
}