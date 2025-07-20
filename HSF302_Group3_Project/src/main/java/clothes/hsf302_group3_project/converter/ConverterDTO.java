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

    public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        dto.setProduct(convertToProductDTO(orderItem.getProduct()));
        // Nếu cần, thêm thông tin khác như orderId...
        return dto;
    }

    public DiscountEventDTO convertToDiscountEventDTO(DiscountEvent discountEvent) {
        if (discountEvent == null) {
            return null;
        }

        DiscountEventDTO dto = new DiscountEventDTO();
        dto.setId(discountEvent.getId());
        dto.setName(discountEvent.getName());
//        dto.setDescription(discountEvent.getNote()); // ✅ dùng note thay vì description
        dto.setStartDate(discountEvent.getStartDate());
        dto.setEndDate(discountEvent.getEndDate());
        // Nếu cần thêm các trường khác thì bạn bổ sung ở đây

        return dto;
    }

}
