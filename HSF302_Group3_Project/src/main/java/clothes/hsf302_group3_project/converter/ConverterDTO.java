package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.response.*;
import clothes.hsf302_group3_project.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ConverterDTO {

    private final ModelMapper modelMapper;
    private final DateTimeConverter dateTimeConverter;

    public ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setStatus(product.getStatus());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }

        // Convert sizes
        if (product.getSizes() != null) {
            List<ProductSizeDTO> sizeDTOs = product.getSizes().stream()
                    .map(this::convertToProductSizeDTO)
                    .collect(Collectors.toList());
            dto.setSizes(sizeDTOs);
        }

        return dto;
    }

    public ProductSizeDTO convertToProductSizeDTO(ProductSize productSize) {
        ProductSizeDTO dto = new ProductSizeDTO();
        dto.setId(productSize.getId());
        dto.setSizeName(productSize.getName());
        dto.setQuantity(productSize.getQuantity());
        dto.setProductId(productSize.getProduct().getId());
        return dto;
    }

    public Product convertToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setStatus(productDTO.getStatus());
        product.setCreatedAt(productDTO.getCreatedAt());
        product.setImageUrl(productDTO.getImageUrl());
        return product;
    }

    public ProductSize convertToProductSize(ProductSizeDTO productSizeDTO) {
        ProductSize entity = new ProductSize();
        entity.setId(productSizeDTO.getId());
        entity.setName(productSizeDTO.getSizeName());
        entity.setQuantity(productSizeDTO.getQuantity());
        return entity;
    }

    public UserDTO convertToUserDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setCreatedAt(dateTimeConverter.toString(user.getCreatedAt()));
        return dto;
    }

    public CartDTO convertToCartDTO(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setSum(cart.getSum());
        dto.setUser(convertToUserDTO(cart.getUser()));
        return dto;
    }

    public CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setCart(convertToCartDTO(cartItem.getCart()));
        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(cartItem.getPrice());
        dto.setProduct(convertToProductDTO(cartItem.getProduct()));
        return dto;
    }

    public OrderDTO convertToOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        OrderDTO dto = new OrderDTO();
        modelMapper.map(order, dto);
        dto.setCreatedAt(dateTimeConverter.toString(order.getCreatedAt()));
        dto.setCustomer(convertToUserDTO(order.getCustomer()));
        dto.setShipper(convertToUserDTO(order.getShipper()));
        return dto;
    }

}
