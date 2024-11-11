package com.techie.mappers;

import com.techie.domain.CategoryDto;
import com.techie.domain.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements Mapper<CategoryEntity, CategoryDto> {

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
