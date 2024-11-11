package com.techie.service;

import com.techie.domain.CategoryDto;
import com.techie.domain.CategoryEntity;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity category);

  //  Page<ProductDto> getProductsByCategory(int page , int size, Integer categoryId);

    Page<CategoryDto> fetchAllCategories(int page , int size);

    CategoryDto fetchCategoryById(Integer id);

    boolean isExists(Integer id);

    void deleteById(Integer id);

    void updateCategory(Integer id ,CategoryEntity updateCategory);
}
