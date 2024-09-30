package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.shippo.ShipmentDto;
import com.techie.ecommerce.domain.model.shippo.ShipmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapperImpl implements Mapper<ShipmentEntity, ShipmentDto> {

    private ModelMapper mapper;

    public ShipmentMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ShipmentDto mapTo(ShipmentEntity shipmentEntity) {
        return mapper.map(shipmentEntity, ShipmentDto.class);
    }

    @Override
    public ShipmentEntity mapFrom(ShipmentDto shipmentDto) {
        return mapper.map(shipmentDto, ShipmentEntity.class);
    }
}
