package com.techie.ecommerce.controller.apiImpl;

import com.techie.ecommerce.domain.dto.shippo.ShipmentDto;
import com.techie.ecommerce.domain.model.shippo.ShipmentEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.ShippingService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shipments")
public class ShippingController {


    private static final Log log = LogFactory.getLog(ShippingController.class);
    private final ShippingService shippingService;
    private final Mapper<ShipmentEntity,ShipmentDto> mapper;

    public ShippingController(ShippingService shippingService, Mapper<ShipmentEntity, ShipmentDto> mapper) {
        this.shippingService = shippingService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ShipmentDto> createShipment (@RequestBody ShipmentDto shipmentDto){
         ShipmentEntity shipmentEntity = mapper.mapFrom(shipmentDto);
        ShipmentEntity savedCreation = shippingService.createShipment(shipmentEntity);
        ShipmentDto dto = mapper.mapTo(savedCreation);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/{trackingNumber}")
    public ResponseEntity<ShipmentDto> getShipmentByTrackingNumber(@PathVariable String trackingNumber){
        Optional<ShipmentEntity> shipmentEntity = shippingService.getShipmentByTrackingNumber(trackingNumber);
        return shipmentEntity.map(shipment -> {
            ShipmentDto responseDto = mapper.mapTo(shipment);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    public  ResponseEntity<List<ShipmentDto>> getAllShipments(){
         List<ShipmentEntity> shipmentEntities = shippingService.getAllShipments();
        List<ShipmentDto> shipmentDtos = shipmentEntities
                .stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(shipmentDtos);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ShipmentDto> updateShipment(
            @PathVariable Long id,
            @RequestBody ShipmentDto shipmentDto
    ){
      //  shipmentDto.setId(id);
        ShipmentEntity shipmentEntity = mapper.mapFrom(shipmentDto);
        ShipmentEntity updatedShipment = shippingService.updateShipment(id, shipmentEntity);
        return new ResponseEntity<>(
                mapper.mapTo(updatedShipment),
                HttpStatus.OK);
    }
     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id){
        shippingService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }
}
