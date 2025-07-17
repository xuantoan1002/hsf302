package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.response.ProvinceDTO;
import clothes.hsf302_group3_project.entity.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {
    List<ProvinceDTO> getAllProvinces();
    Optional<ProvinceDTO> getProvinceById(Long id);
    Optional<ProvinceDTO> getProvinceByCode(String code);
}
