package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {

    Optional<Ward> findById(Long id);

    List<Ward> findByDistrictId(Long districtId);

    @Query("SELECT w FROM Ward w WHERE w.district.id = :districtId ORDER BY w.name")
    List<Ward> findByDistrictIdOrderByName(@Param("districtId") Long districtId);

    @Query("SELECT w FROM Ward w WHERE w.district.code = :districtCode ORDER BY w.name")
    List<Ward> findByDistrictCodeOrderByName(@Param("districtCode") String districtCode);

    @Query("SELECT w FROM Ward w WHERE w.district.province.id = :provinceId ORDER BY w.name")
    List<Ward> findByProvinceIdOrderByName(@Param("provinceId") Long provinceId);

    List<Ward> findByNameContaining(String name);
}
