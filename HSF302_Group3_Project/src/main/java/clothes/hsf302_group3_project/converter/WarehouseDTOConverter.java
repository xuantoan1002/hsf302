package clothes.hsf302_group3_project.converter;

import clothes.hsf302_group3_project.dto.response.WarehouseDTO;
import clothes.hsf302_group3_project.entity.Warehouse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WarehouseDTOConverter {
    private final ModelMapper modelMapper;
    public WarehouseDTO toDTO(Warehouse warehouse) {
        WarehouseDTO dto = modelMapper.map(warehouse, WarehouseDTO.class);
        String location = warehouse.getWard().getType() + " " + warehouse.getWard().getName()
                + " - " + warehouse.getDistrict().getType() + " " + warehouse.getDistrict().getName()
                + " - " + warehouse.getProvince().getType() + " " + warehouse.getProvince().getName()
                + " - " + warehouse.getStreetAddress();
        dto.setLocation(location);
        return dto;
    }
}
