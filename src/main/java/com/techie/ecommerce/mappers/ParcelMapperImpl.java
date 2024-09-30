package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.shippo.ParcelDto;
import com.techie.ecommerce.domain.model.shippo.Parcel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ParcelMapperImpl implements Mapper<Parcel, ParcelDto> {

    private ModelMapper mapper;

    public ParcelMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ParcelDto mapTo(Parcel parcel) {
        return mapper.map(parcel, ParcelDto.class);
    }

    @Override
    public Parcel mapFrom(ParcelDto parcelDto) {
        return mapper.map(parcelDto, Parcel.class);
    }
}
