package clothes.hsf302_group3_project.service.impl;

import clothes.hsf302_group3_project.entity.OrderStatusHistory;
import clothes.hsf302_group3_project.repository.OrderStatusHistoryRepository;
import clothes.hsf302_group3_project.service.OrderStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryService {

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Override
    public void save(OrderStatusHistory history) {
        orderStatusHistoryRepository.save(history);
    }
}
