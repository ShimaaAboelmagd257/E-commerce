package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> fetchAllproducts();
    ProductDto fetchProductById(Long id);
}
