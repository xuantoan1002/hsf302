package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.dto.request.DiscountEventRequest;
import clothes.hsf302_group3_project.dto.response.DiscountEventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscountService {
    void delete(Long id);
    DiscountEventDTO getDiscountInfo(Integer productId);
    Page<DiscountEventDTO> findAll(Pageable pageable);
    DiscountEventDTO create(DiscountEventRequest request);
    DiscountEventDTO update(Long id, DiscountEventRequest request);
    DiscountEventDTO findById(Long id);
    public Page<DiscountEventDTO> getActiveDiscounts(Pageable pageable);
    public Page<DiscountEventDTO> getInactiveDiscounts(Pageable pageable);
}
