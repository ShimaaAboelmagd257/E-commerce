package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CategoryEntity;

import java.util.List;

public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity category);

    List<ProductDto> getProductsByCategory(Integer categoryId);

    List<CategoryDto> fetchAllCategories();

    CategoryDto fetchCategoryById(Integer id);

    boolean isExists(Integer id);

    void deleteById(Integer id);

    CategoryEntity updateCategory(Integer id ,CategoryEntity updateCategory);
}
