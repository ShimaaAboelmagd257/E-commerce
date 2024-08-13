package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.ProductEntity;

import java.util.List;

public interface ProductService {


    List<ProductDto> fetchAllproducts();
    ProductDto fetchProductById(Long id);
    ProductEntity save(ProductEntity product);
    boolean isExists(Long id);
    void deleteById(Long id);

    List<ProductDto> filterProducts(String title,
                                    Double price,
                                    Double priceMin,
                                    Double priceMax,
                                    Long categoryId,
                                    Integer limit,
                                    Integer offset);

    ProductEntity updateProduct(Long id,ProductEntity updateProduct);
}
