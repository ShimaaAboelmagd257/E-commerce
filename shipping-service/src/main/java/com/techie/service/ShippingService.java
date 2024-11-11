package com.techie.service;

import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.techie.domain.shippo.ShipmentDto;
import com.techie.domain.shippo.TransactionResponseDto;

import java.util.List;

public interface ShippingService {

    TransactionResponseDto createShipment(ShipmentDto shipmentDto) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException;
    ShipmentDto getShipmentByTrackingNumber(String trackingNumber);
    List<ShipmentDto> getAllShipments();
    ShipmentDto updateShipment(Long id,ShipmentDto shipmentDto);
    void deleteShipment(Long id);
}
