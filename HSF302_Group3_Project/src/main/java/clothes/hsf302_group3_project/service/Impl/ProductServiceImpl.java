package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.response.ProductDTO;
import clothes.hsf302_group3_project.dto.response.ProductSizeDTO;
import clothes.hsf302_group3_project.entity.Category;
import clothes.hsf302_group3_project.entity.Product;
import clothes.hsf302_group3_project.entity.ProductImage;
import clothes.hsf302_group3_project.entity.ProductSize;
import clothes.hsf302_group3_project.exception.ResourceNotFoundException;
import clothes.hsf302_group3_project.repository.ProductImageRepository;
import clothes.hsf302_group3_project.repository.ProductRepository;
import clothes.hsf302_group3_project.repository.ProductSizeRepository;
import clothes.hsf302_group3_project.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final List<String> VALID_SIZES = List.of("S", "M", "L", "XL");


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ConverterDTO converterDTO;
    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(converterDTO::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        // Validate sizes
        productDTO.getSizes().forEach(ps -> {
            if (!VALID_SIZES.contains(ps.getSize())) {
                throw new IllegalArgumentException("Invalid size: " + ps.getSize());
            }
        });

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        // Tính tổng số lượng từ các size
        int totalStock = productDTO.getSizes().stream()
                .mapToInt(ProductSizeDTO::getQuantity)
                .sum();
        product.setStock(totalStock);

        product.setStatus(productDTO.getStatus());
        product.setCreatedAt(LocalDateTime.now());

        // Set category nếu có
        if (productDTO.getCategoryId() != null) {
            Category category = new Category();
            category.setId(productDTO.getCategoryId());
            product.setCategory(category);
        }

        Product savedProduct = productRepository.save(product);

        // Lưu sizes
        productDTO.getSizes().forEach(ps -> {
            if (ps.getQuantity() > 0) { // Chỉ lưu size có số lượng > 0
                ProductSize productSize = new ProductSize();
                productSize.setProduct(savedProduct);
                productSize.setSize(ps.getSize());
                productSize.setQuantity(ps.getQuantity());
                productSizeRepository.save(productSize);
            }
        });

        // Lưu ảnh sản phẩm
        if (productDTO.getImages() != null && !productDTO.getImages().isEmpty()) {
            productDTO.getImages().forEach(imageDTO -> {
                ProductImage image = new ProductImage();
                image.setProduct(savedProduct);
                image.setImageUrl(imageDTO.getImageUrl());
                productImageRepository.save(image);
            });
        }

        return converterDTO.convertToProductDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Cập nhật thông tin
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setStatus(productDTO.getStatus());

        Product updatedProduct = productRepository.save(existingProduct);
        return converterDTO.convertToProductDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getFeaturedProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductDTO> featuredProducts = new ArrayList<>();

        for (int i = 0; i < allProducts.size() && i < 10; i++) {
            Product product = allProducts.get(i);
            ProductDTO dto = converterDTO.convertToProductDTO(product);
            featuredProducts.add(dto);
        }
        return featuredProducts;
    }

    @Override
    public List<ProductDTO> searchProducts(String query) {
        List<Product> products = productRepository.searchProducts(query.toLowerCase());
        return products.stream()
                .map(converterDTO::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(converterDTO::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndSort(Integer categoryId, String sortBy, String direction) {
        List<ProductDTO> products = getProductsByCategory(categoryId);
        return sortProducts(products, sortBy, direction);
    }

    @Override
    public List<ProductDTO> getAllProductsSorted(String sortBy, String direction) {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(converterDTO::convertToProductDTO)
                .collect(Collectors.toList());
        return sortProducts(productDTOs, sortBy, direction);
    }

    @Override
    public List<ProductDTO> sortProducts(List<ProductDTO> products, String sortBy, String direction) {
        Comparator<ProductDTO> comparator;

        switch (sortBy.toLowerCase()) {
            case "price":
                comparator = Comparator.comparing(ProductDTO::getPrice);
                break;
            case "name":
                comparator = Comparator.comparing(ProductDTO::getName);
                break;
            default:
                comparator = Comparator.comparing(ProductDTO::getId);
        }

        if ("desc".equalsIgnoreCase(direction)) {
            comparator = comparator.reversed();
        }

        return products.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductDetail(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return converterDTO.convertToProductDTO(product);
    }


}