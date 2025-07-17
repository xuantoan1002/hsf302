package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    Optional<District> findById(Long id);
    @Query("SELECT d FROM District d WHERE d.province.id = :provinceId ORDER BY d.name")
    List<District> findByProvinceIdOrderByName(@Param("provinceId") Long provinceId);

    @Query("SELECT d FROM District d WHERE d.province.code = :provinceCode ORDER BY d.name")
    List<District> findByProvinceCodeOrderByName(@Param("provinceCode") String provinceCode);

    List<District> findByNameContaining(String name);
}