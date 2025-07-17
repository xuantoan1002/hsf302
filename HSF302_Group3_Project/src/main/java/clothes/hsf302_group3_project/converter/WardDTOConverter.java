package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.response.WardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WardDTOConverter {
    private final WardDTOConverter wardDTOConverter;
    public WardDTO convertWardDTO(WardDTO wardDTO) {
        return wardDTOConverter.convertWardDTO(wardDTO);
    }
}
