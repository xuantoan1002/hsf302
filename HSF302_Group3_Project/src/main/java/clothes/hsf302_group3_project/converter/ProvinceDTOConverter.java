package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.response.ProvinceDTO;
import clothes.hsf302_group3_project.entity.Province;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProvinceDTOConverter {
    private final ModelMapper modelMapper;
    public ProvinceDTO toDTO(Province province) {
        return modelMapper.map(province, ProvinceDTO.class);
    }
}
