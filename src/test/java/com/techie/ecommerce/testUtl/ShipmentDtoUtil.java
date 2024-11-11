package com.techie.ecommerce.testUtl;

import com.techie.ecommerce.domain.dto.shippo.ShipmentDto;

public class ShipmentDtoUtil {

    public static ShipmentDto createShipmentDto(Long id, String trackingNumber) {
        ShipmentDto shipmentDto = new ShipmentDto();
//        shipmentDto.setId(id);
//        shipmentDto.setTrackingNumber(trackingNumber);
//        shipmentDto.setAddress("123 Main St, City, Country");
//        shipmentDto.setWeight(1.5);
        return shipmentDto;
    }

    public static ShipmentDto createDefaultShipmentDto() {
        return createShipmentDto(1L, "12345");
    }

    public static ShipmentDto createInvalidShipmentDto() {
       ShipmentDto shipmentDto = new ShipmentDto();
//        shipmentDto.setId(null);
//        shipmentDto.setTrackingNumber("");
        return shipmentDto;
    }
}

