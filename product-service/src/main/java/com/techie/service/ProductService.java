package com.techie.service;

import com.techie.domain.ProductCreationEntity;
import com.techie.domain.ProductDto;
import com.techie.domain.ProductFilter;
import org.springframework.data.domain.Page;

public interface ProductService {


    Page<ProductDto> fetchAllProducts(int page, int size);
    ProductDto fetchProductById(Integer id);
   // ProductEntity save(ProductEntity product);
   ProductCreationEntity createProduct(ProductCreationEntity creation);
    boolean isExists(Integer id);
    void deleteById(Integer id);

    Page<ProductDto> filterProducts(ProductFilter productFilter, int page, int size);

    void updateProduct(Integer id, ProductCreationEntity updateProduct);
}
