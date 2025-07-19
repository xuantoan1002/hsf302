package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.response.*;
import clothes.hsf302_group3_project.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ConverterDTO {

    private final ModelMapper modelMapper;
    private final DateTimeConverter dateTimeConverter;

    public ProductDTO convertToProductDTO(Product product) {
        if (product == null) {
            return null;
        }
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
    public DiscountEventDTO convertToDiscountEventDTO(DiscountEvent discountEvent) {
        DiscountEventDTO dto = new DiscountEventDTO();
        dto.setId(discountEvent.getId());
        dto.setTargetType(discountEvent.getTargetType());
        dto.setName(discountEvent.getName());
        dto.setStartDate(discountEvent.getStartDate());
        dto.setEndDate(discountEvent.getEndDate());
        dto.setDiscountType(discountEvent.getDiscountType());
        dto.setDiscountValue(discountEvent.getDiscountValue());
        dto.setProductId(discountEvent.getProduct().getId());
        dto.setProductName(discountEvent.getProduct().getName());
        dto.setNote(discountEvent.getNote());
        return dto;
    }

    public ProductImageDTO convertToProductImageDTO(ProductImage image) {
        if (image == null) return null;
        ProductImageDTO dto = new ProductImageDTO();
        dto.setId(image.getId());
        dto.setImageUrl(image.getImageUrl());
        dto.setProductId(image.getProduct() != null ? image.getProduct().getId() : null);
        return dto;
    }

    public ProductSizeDTO convertToProductSizeDTO(ProductSize productSize) {
        if (productSize == null) {
            return null;
        }
        ProductSizeDTO dto = new ProductSizeDTO();
        dto.setId(productSize.getId());
        dto.setSize(productSize.getSize());
        dto.setQuantity(productSize.getQuantity());
        return dto;
    }

    public UserDTO convertToUserDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = modelMapper.map(user, UserDTO.class);
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

    public OrderItemDTO toOrderItemDTO(OrderItem oi) {
        OrderItemDTO oiDTO = new OrderItemDTO();
        modelMapper.map(oi, oiDTO);
        oiDTO.setId(oi.getId());
        oiDTO.setQuantity(oi.getQuantity());
        oiDTO.setPrice(oi.getPrice());
        oiDTO.setProduct(convertToProductDTO(oi.getProduct()));
        return oiDTO;
    }
}
