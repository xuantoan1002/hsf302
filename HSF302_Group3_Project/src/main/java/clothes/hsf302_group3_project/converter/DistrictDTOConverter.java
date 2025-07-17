package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.response.DistrictDTO;
import clothes.hsf302_group3_project.entity.District;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DistrictDTOConverter {
    private final ModelMapper modelMapper;
    public DistrictDTO toDTO(District district) {
        DistrictDTO dto = modelMapper.map(district, DistrictDTO.class);
        return dto;
    }
}
