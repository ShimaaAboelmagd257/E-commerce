package com.techie.mappers;

import com.techie.domain.ProductCreation;
import com.techie.domain.ProductCreationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductCreationMapperImpl implements Mapper<ProductCreationEntity, ProductCreation>{


    private ModelMapper mapper;

    public ProductCreationMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ProductCreation mapTo(ProductCreationEntity productCreationEntity) {
        return mapper.map(productCreationEntity,ProductCreation.class);
    }

    @Override
    public ProductCreationEntity mapFrom(ProductCreation creation) {
        return mapper.map(creation,ProductCreationEntity.class);
    }
}
