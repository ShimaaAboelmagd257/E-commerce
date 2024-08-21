package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.dto.ProductFilter;
import com.techie.ecommerce.domain.model.ProductCreationEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {


    Page<ProductDto> fetchAllProducts(int page, int size);
    ProductDto fetchProductById(Integer id);
   // ProductEntity save(ProductEntity product);
   ProductCreationEntity createProduct(ProductCreationEntity creation);
    boolean isExists(Integer id);
    void deleteById(Integer id);

    Page<ProductDto> filterProducts(ProductFilter productFilter, int page,int size);

    void updateProduct(Integer id, ProductCreationEntity updateProduct);
}
