package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.model.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements Mapper<CategoryEntity, CategoryDto>{

    private ModelMapper mapper;

    public CategoryMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CategoryDto mapTo(CategoryEntity categoryEntity) {
        return mapper.map(categoryEntity,CategoryDto.class);
    }

    @Override
    public CategoryEntity mapFrom(CategoryDto categoryDto) {
        return mapper.map(categoryDto,CategoryEntity.class);
    }
}
