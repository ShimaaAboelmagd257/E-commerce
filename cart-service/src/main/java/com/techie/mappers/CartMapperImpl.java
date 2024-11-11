package com.techie.mappers;

import com.techie.domain.CartDto;
import com.techie.domain.CartEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CartMapperImpl implements Mapper<CartEntity, CartDto> {

    private ModelMapper modelMapper;
    public CartMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public CartDto mapTo(CartEntity cartEntity) {
        return modelMapper.map(cartEntity,CartDto.class);
    }

    @Override
    public CartEntity mapFrom(CartDto cartDto) {
        return modelMapper.map(cartDto, CartEntity.class);
    }
}
