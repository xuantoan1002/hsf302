package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.ProductSize;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductSizeRepository extends CrudRepository<ProductSize, Integer> {
    @Query("SELECT ps FROM ProductSize ps LEFT JOIN FETCH ps.product")
    List<ProductSize> findAllWithProductInfo();
}
