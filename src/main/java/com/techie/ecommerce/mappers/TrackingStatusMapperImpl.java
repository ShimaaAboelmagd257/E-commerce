package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.shippo.TrackingStatusDTO;
import com.techie.ecommerce.domain.model.shippo.TrackingStatusEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TrackingStatusMapperImpl implements Mapper<TrackingStatusEntity, TrackingStatusDTO> {

    private ModelMapper mapper;

    public TrackingStatusMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TrackingStatusDTO mapTo(TrackingStatusEntity trackingStatusEntity) {
        return mapper.map(trackingStatusEntity, TrackingStatusDTO.class);
    }

    @Override
    public TrackingStatusEntity mapFrom(TrackingStatusDTO trackingStatusDTO) {
        return mapper.map(trackingStatusDTO, TrackingStatusEntity.class);
    }
}
