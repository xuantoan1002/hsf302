package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findById(Long id);
    @Query("SELECT p FROM Province p ORDER BY p.name")
    List<Province> findAllOrderByName();
}
