package clothes.hsf302_group3_project.service.impl;

import clothes.hsf302_group3_project.entity.Delivery;
import clothes.hsf302_group3_project.repository.DeliveryRepository;
import clothes.hsf302_group3_project.service.DeliveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public List<Delivery> getDeliveriesByShipperUsername(String username) {
        return deliveryRepository.findByShipperUsername(username);
    }
}
