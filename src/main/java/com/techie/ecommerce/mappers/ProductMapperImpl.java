package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements Mapper<ProductEntity, ProductDto>{

    public ProductMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private ModelMapper modelMapper;
    @Override
    public ProductDto mapTo(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }

    @Override
    public ProductEntity mapFrom(ProductDto productDto) {
        return modelMapper.map(productDto, ProductEntity.class);
    }
}
