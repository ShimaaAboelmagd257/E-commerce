package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.dto.ProductFilter;
import com.techie.ecommerce.domain.model.ProductCreationEntity;

import java.util.List;

public interface ProductService {


    List<ProductDto> fetchAllproducts();
    ProductDto fetchProductById(Integer id);
   // ProductEntity save(ProductEntity product);
   ProductCreationEntity createProduct(ProductCreationEntity creation);
    boolean isExists(Integer id);
    void deleteById(Integer id);

    List<ProductDto> filterProducts(ProductFilter productFilter);

    void updateProduct(Integer id, ProductCreationEntity updateProduct);
}
