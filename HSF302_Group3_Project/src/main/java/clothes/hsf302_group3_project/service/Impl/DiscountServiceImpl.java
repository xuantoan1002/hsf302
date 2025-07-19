package clothes.hsf302_group3_project.service.Impl;

import clothes.hsf302_group3_project.converter.ConverterDTO;
import clothes.hsf302_group3_project.dto.request.DiscountEventRequest;
import clothes.hsf302_group3_project.dto.response.DiscountEventDTO;
import clothes.hsf302_group3_project.entity.DiscountEvent;
import clothes.hsf302_group3_project.entity.Product;
import clothes.hsf302_group3_project.enums.TargetType;
import clothes.hsf302_group3_project.repository.DiscountEventRepository;
import clothes.hsf302_group3_project.repository.ProductRepository;
import clothes.hsf302_group3_project.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
    @Override
    public DiscountEventDTO create(DiscountEventRequest request) {
        if (!isValidDateRange(request.getStartDate(), request.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        DiscountEvent event = new DiscountEvent();
        event.setName(request.getName());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setDiscountType(request.getDiscountType());
        event.setDiscountValue(request.getDiscountValue());
        event.setTargetType(request.getTargetType());
        event.setNote(request.getNote());

        if (request.getTargetType() == TargetType.ALL) {
            event.setProduct(null);
        } else if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            event.setProduct(product);
        }

        return converterDTO.convertToDiscountEventDTO(discountEventRepository.save(event));
    }
    @Transactional
    @Override
    public DiscountEventDTO update(Long id, DiscountEventRequest request) {
        if (!isValidDateRange(request.getStartDate(), request.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        DiscountEvent existing = discountEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        existing.setName(request.getName());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        existing.setDiscountType(request.getDiscountType());
        existing.setDiscountValue(request.getDiscountValue());
        existing.setTargetType(request.getTargetType());
        existing.setNote(request.getNote());

        if (request.getTargetType() == TargetType.ALL) {
            existing.setProduct(null);
        } else if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            existing.setProduct(product);
        }

        return converterDTO.convertToDiscountEventDTO(discountEventRepository.save(existing));
    }

    @Override
    public DiscountEventDTO findById(Long id) {
        DiscountEvent discountEvent = discountEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return converterDTO.convertToDiscountEventDTO(discountEvent);
    }

    @Override
    public List<DiscountEventDTO> findAll() {
        List<DiscountEvent> discountEvents = discountEventRepository.findAllWithProduct();
        return discountEvents.stream()
                .map(converterDTO::convertToDiscountEventDTO)
                .collect(Collectors.toList());
    }
    private DiscountEvent mapRequestToEntity(DiscountEventRequest req, DiscountEvent event) {
        event.setName(req.getName());
        event.setStartDate(req.getStartDate());
        event.setEndDate(req.getEndDate());
        event.setDiscountType(req.getDiscountType());
        event.setDiscountValue(req.getDiscountValue());
        event.setNote(req.getNote());
        event.setTargetType(req.getTargetType());
        if (req.getProductId() != null) {
            Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            event.setProduct(product);
        } else {
            event.setProduct(null);
        }
        return event;
    }
    private boolean isValidDateRange(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null && startDate.isBefore(endDate);
    }
}
