package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.Cart;
import clothes.hsf302_group3_project.entity.CartItem;
import clothes.hsf302_group3_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByIdInAndCart_User_Id(List<Long> ids, Long userId);
    CartItem findByCartAndProduct(Cart cart, Product product);

}
