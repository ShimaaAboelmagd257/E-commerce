package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.shippo.AddressDto;
import com.techie.ecommerce.domain.model.shippo.AddressEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl implements Mapper<AddressEntity, AddressDto>{

    private ModelMapper mapper;

    public AddressMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    public AddressDto mapTo(AddressEntity addressEntity) {
        return mapper.map(addressEntity, AddressDto.class);
    }

    @Override
    public AddressEntity mapFrom(AddressDto addressDto) {
        return mapper.map(addressDto, AddressEntity.class);
    }
}
