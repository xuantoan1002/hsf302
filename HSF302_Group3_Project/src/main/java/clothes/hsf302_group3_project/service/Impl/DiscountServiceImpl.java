package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.response.DiscountEventDTO;
import clothes.hsf302_group3_project.entity.DiscountEvent;
import clothes.hsf302_group3_project.entity.Product;
import clothes.hsf302_group3_project.enums.TargetType;
import clothes.hsf302_group3_project.repository.DiscountEventRepository;
import clothes.hsf302_group3_project.repository.ProductRepository;
import clothes.hsf302_group3_project.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountEventRepository discountEventRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ConverterDTO converterDTO;
    @Override
    public void delete(Long id) {
        if (!discountEventRepository.existsById(id)) {
            throw new RuntimeException("Event not found");
        }
        discountEventRepository.deleteById(id);
    }

    @Override
    public DiscountEventDTO getDiscountInfo(Integer productId) {
            LocalDate today = LocalDate.now();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("product not found"));
            List<DiscountEvent> specificEvents = discountEventRepository
                    .findByProduct_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(productId, today, today);
            DiscountEvent discount = null;
            if (!specificEvents.isEmpty()) {
                discount = specificEvents.get(0);
            } else {
                List<DiscountEvent> globalEvents = discountEventRepository
                        .findByTargetTypeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(TargetType.ALL, today, today);

                if (!globalEvents.isEmpty()) {
                    discount = globalEvents.get(0);
                }
            }
            if (discount == null) {
                return null;
            }
            return converterDTO.convertToDiscountEventDTO(discount);
    }

    @Override
    public List<DiscountEventDTO> findAll() {
        List<DiscountEvent> discountEvents = discountEventRepository.findAllWithProduct();
        return discountEvents.stream()
                .map(converterDTO::convertToDiscountEventDTO)
                .collect(Collectors.toList());
    }
}
