package com.techie.mappers;

import com.techie.domain.ProductEntity;
import com.techie.domain.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductResponceMapperImpl implements Mapper<ProductEntity , ProductResponse> {
    private ModelMapper modelMapper;

    public ProductResponceMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponse mapTo(ProductEntity entity) {
        return modelMapper.map(entity, ProductResponse.class);
    }

    @Override
    public ProductEntity mapFrom(ProductResponse response) {
        return modelMapper.map(response , ProductEntity.class);
    }
}
