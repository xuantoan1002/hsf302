package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.response.ProductDTO;
import clothes.hsf302_group3_project.entity.Product;
import org.springframework.stereotype.Component;

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
        return dto;
    }

    public UserDTO convertToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
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
