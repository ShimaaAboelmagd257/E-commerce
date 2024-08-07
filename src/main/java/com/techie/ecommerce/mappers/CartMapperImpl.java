package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.CartDto;
import com.techie.ecommerce.domain.model.CartEntity;
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
