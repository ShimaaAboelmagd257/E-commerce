package com.techie.ecommerce.testUtl;

import com.techie.ecommerce.domain.dto.CategoryDto;

public class CategoryDtoUtil {

    public static CategoryDto createCategory(){
        return CategoryDto
                .builder()
                .name("Category")
                .image("https://i.imgur.com/UlxxXyG.jpeg")
                .build();
    }
}
