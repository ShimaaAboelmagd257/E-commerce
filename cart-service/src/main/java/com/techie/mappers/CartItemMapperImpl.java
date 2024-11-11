package com.techie.mappers;

import com.techie.domain.CartItemDto;
import com.techie.domain.CartItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapperImpl implements Mapper<CartItemEntity, CartItemDto> {



    private ModelMapper modelMapper;
    public CartItemMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CartItemDto mapTo(CartItemEntity cartItemEntity) {
        return modelMapper.map(cartItemEntity,CartItemDto.class);
    }

    @Override
    public CartItemEntity mapFrom(CartItemDto cartItemDto) {
        return modelMapper.map(cartItemDto,CartItemEntity.class);
    }
}