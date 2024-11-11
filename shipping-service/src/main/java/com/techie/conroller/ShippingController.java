package com.techie.conroller;

import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.techie.domain.shippo.ShipmentDto;
import com.techie.domain.shippo.TransactionResponseDto;
import com.techie.domain.shippoentity.ShipmentEntity;
import com.techie.mappers.Mapper;
import com.techie.service.ShippingService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShippingController {


    private static final Log log = LogFactory.getLog(ShippingController.class);
    private final ShippingService shippingService;
    private final Mapper<ShipmentEntity, ShipmentDto> mapper;

    public ShippingController(ShippingService shippingService, Mapper<ShipmentEntity, ShipmentDto> mapper) {
        this.shippingService = shippingService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createShipment (@RequestBody ShipmentDto shipmentDto) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
      //   ShipmentEntity shipmentEntity = mapper.mapFrom(shipmentDto);
        TransactionResponseDto savedCreation = shippingService.createShipment(shipmentDto);
     //   ShipmentDto dto = mapper.mapTo(savedCreation);
        log.info("shipment exception !!!!!!!!!!");
        return new ResponseEntity<>(savedCreation, HttpStatus.CREATED);
    }
    @GetMapping("/{trackingNumber}")
    public ResponseEntity<ShipmentDto> getShipmentByTrackingNumber(@PathVariable String trackingNumber){
        try {
            ShipmentDto responseDto = shippingService.getShipmentByTrackingNumber(trackingNumber);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);
        }catch (Exception e){
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping
    public  ResponseEntity<List<ShipmentDto>> getAllShipments(){
         List<ShipmentDto> shipmentDtos = shippingService.getAllShipments();
//        List<ShipmentDto> shipmentDtos = shipmentEntities
//                .stream()
//                .map(mapper::mapTo)
//                .collect(Collectors.toList());
        return ResponseEntity.ok(shipmentDtos);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ShipmentDto> updateShipment(
            @PathVariable Long id,
            @RequestBody ShipmentDto shipmentDto
    ){
      //  shipmentDto.setId(id);
       // ShipmentEntity shipmentEntity = mapper.mapFrom(shipmentDto);
        ShipmentDto updatedShipment = shippingService.updateShipment(id, shipmentDto);
        return new ResponseEntity<>(
              updatedShipment,
                HttpStatus.OK);
    }
     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id){
        shippingService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }
}
