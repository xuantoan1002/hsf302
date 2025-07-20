package clothes.hsf302_group3_project.service;

import clothes.hsf302_group3_project.entity.Delivery;

import java.util.List;

public interface DeliveryService {
    List<Delivery> getDeliveriesByShipperUsername(String username);
}
