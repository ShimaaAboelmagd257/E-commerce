package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.shippo.ShipmentEntity;
import com.techie.ecommerce.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService{

    @Autowired
    private ShipmentRepository repository;
    private RestTemplate restTemplate;
    @Value("${shippo.api.url}")
    private String shippoApiUrl;

    @Value("${shippo.api.key}")
    private String shippoApiToken;

    @Override
    public ShipmentEntity createShipment(ShipmentEntity shipmentEntity) {
        ResponseEntity<ShipmentEntity> response = restTemplate.postForEntity(shippoApiUrl, shipmentEntity, ShipmentEntity.class);
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            ShipmentEntity createdShipment = response.getBody();
            if (createdShipment == null) {
                throw new RuntimeException("Failed to save Shipment to external API");
            }
            return createdShipment;
        } else {
            throw new RuntimeException("Failed to save Shipment to external API");
        }
    }

    @Override
    public Optional<ShipmentEntity> getShipmentByTrackingNumber(String trackingNumber) {
        return null;
    }

    @Override
    public List<ShipmentEntity> getAllShipments() {
        return List.of();
    }

    @Override
    public ShipmentEntity updateShipment(Long id, ShipmentEntity shipmentEntity) {
        return null;
    }

    @Override
    public void deleteShipment(Long id) {

    }
}
