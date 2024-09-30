package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.shippo.RateDto;
import com.techie.ecommerce.domain.model.shippo.RateEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RateMapperImpl implements Mapper<RateEntity, RateDto>{

    private ModelMapper mapper;

    public RateMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public RateDto mapTo(RateEntity rateEntity) {
        return mapper.map(rateEntity, RateDto.class);
    }

    @Override
    public RateEntity mapFrom(RateDto rateDto) {
        return mapper.map(rateDto, RateEntity.class);
    }
}
