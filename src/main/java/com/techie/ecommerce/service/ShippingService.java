package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.shippo.ShipmentEntity;

import java.util.List;
import java.util.Optional;

public interface ShippingService {

    ShipmentEntity createShipment(ShipmentEntity shipmentEntity);
    Optional<ShipmentEntity> getShipmentByTrackingNumber(String trackingNumber);
    List<ShipmentEntity> getAllShipments();
    ShipmentEntity updateShipment(Long id,ShipmentEntity shipmentEntity);
    void deleteShipment(Long id);
}
