package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.dto.response.ProvinceDTO;
import clothes.hsf302_group3_project.entity.Province;
import clothes.hsf302_group3_project.service.ProvinceService;

import java.util.List;
import java.util.Optional;

public class ProvinceServiceImpl implements ProvinceService {
    @Override
    public List<ProvinceDTO> getAllProvinces() {
        return List.of();
    }

    @Override
    public Optional<ProvinceDTO> getProvinceById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ProvinceDTO> getProvinceByCode(String code) {
        return Optional.empty();
    }
}
