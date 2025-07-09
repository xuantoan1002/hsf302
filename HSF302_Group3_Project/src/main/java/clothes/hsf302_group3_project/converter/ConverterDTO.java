package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.response.CartDTO;
import clothes.hsf302_group3_project.dto.response.CartItemDTO;
import clothes.hsf302_group3_project.dto.response.ProductDTO;
import clothes.hsf302_group3_project.dto.response.UserDTO;
import clothes.hsf302_group3_project.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setStatus(product.getStatus());
        return dto;
    }

    public UserDTO convertToUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setCreatedAt(dateTimeConverter.toString(user.getCreatedAt()));
        dto.setToShipperAt(dateTimeConverter.toString(user.getToShipperAt()));
        return dto;
    }

    public CartDTO convertToCartDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setSum(cart.getSum());
        dto.setUser(convertToUserDTO(cart.getUser()));
        return dto;
    }

    public CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setCart(convertToCartDTO(cartItem.getCart()));
        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(cartItem.getPrice());
        dto.setProduct(convertToProductDTO(cartItem.getProduct()));
        return dto;
    }
}
