package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.request.DiscountEventRequest;
import clothes.hsf302_group3_project.dto.response.DiscountEventDTO;

import java.util.List;

public interface DiscountService {
    void delete(Long id);
    DiscountEventDTO getDiscountInfo(Integer productId);
    List<DiscountEventDTO> findAll();
    DiscountEventDTO create(DiscountEventRequest request);
    DiscountEventDTO update(Long id, DiscountEventRequest request);
    DiscountEventDTO findById(Long id);
}
