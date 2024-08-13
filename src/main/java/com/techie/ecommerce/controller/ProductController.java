package com.techie.ecommerce.controller;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }
    @GetMapping
    public List<ProductDto> getAllProducts(){
        return service.fetchAllproducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        return service.fetchProductById(id);
    }

}
