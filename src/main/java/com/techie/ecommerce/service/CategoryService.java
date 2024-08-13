package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CategoryEntity;

import java.util.List;

public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity category);

    List<ProductDto> getProductsByCategory(Long categoryId);

    List<CategoryDto> fetchAllCategories();

    CategoryDto fetchCategoryById(Long id);

    boolean isExists(Long id);

    void deleteById(Long id);

    CategoryEntity updateCategory(Long id ,CategoryEntity updateCategory);
}
