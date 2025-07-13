package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProductId(Integer productId);

    // Phương thức đã có trước đó
    List<ProductImage> findByProductIdOrderByIdAsc(Integer productId);
}
