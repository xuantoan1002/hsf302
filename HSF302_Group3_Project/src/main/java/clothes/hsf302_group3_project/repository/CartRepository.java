package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.Cart;
import clothes.hsf302_group3_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser(User user);
}
