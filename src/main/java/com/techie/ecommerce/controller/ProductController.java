package com.techie.ecommerce.controller;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.ProductEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final Mapper<ProductEntity, ProductDto> mapper;
    private final ProductService service;

    @Autowired
    public ProductController(Mapper<ProductEntity, ProductDto> mapper, ProductService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        ProductEntity product = mapper.mapFrom(productDto);
        ProductEntity savedProduct = service.save(product);
        ProductDto dto = mapper.mapTo(savedProduct);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return service.fetchAllproducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        return service.fetchProductById(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id , @RequestBody ProductDto productDto){
        if(!service.isExists(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        productDto.setId(id);
        ProductEntity updateProduct = mapper.mapFrom(productDto);
        ProductEntity product = service.updateProduct(id,updateProduct);
        ProductDto dto = mapper.mapTo(product);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductDto>> filterProducts(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "price_min", required = false) Double priceMin,
            @RequestParam(value = "price_max", required = false) Double priceMax,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset) {

        List<ProductDto> products = service.filterProducts(title, price, priceMin, priceMax, categoryId, limit, offset);
        return ResponseEntity.ok(products);
    }
}
