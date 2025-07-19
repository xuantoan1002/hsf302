package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(Integer categoryId);
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', :query, '%'))")
    List<Product> searchProducts(@Param("query") String query);
    Product findProductById(Integer id);

}
