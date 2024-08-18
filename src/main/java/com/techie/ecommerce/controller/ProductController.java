package com.techie.ecommerce.controller;

import com.techie.ecommerce.domain.dto.ProductCreation;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.ProductCreationEntity;
import com.techie.ecommerce.domain.model.ProductEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.security.CustomUserDetailsService;
import com.techie.ecommerce.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Log log = LogFactory.getLog(ProductController.class);

    private final Mapper<ProductEntity, ProductDto> mapper;
    private final Mapper<ProductCreationEntity,ProductCreation> creationMapper;
    private final ProductService service;

    @Autowired
    public ProductController(Mapper<ProductEntity, ProductDto> mapper, Mapper<ProductCreationEntity, ProductCreation> creationMapper, ProductService service) {
        this.mapper = mapper;
        this.creationMapper = creationMapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductCreation creation ){
        ProductCreationEntity product = creationMapper.mapFrom(creation);
        ProductCreationEntity savedCreation = service.createProduct(product);
        ProductCreation dto = creationMapper.mapTo(savedCreation);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @GetMapping
    public List<ProductDto> getAllProducts(){
        return service.fetchAllproducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id){
        return service.fetchProductById(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id , @RequestBody ProductCreation productDto){
        log.info("Received update request for product with ID: "+ id);

        /*if(!service.isExists(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }*/
        productDto.setId(id);
        ProductCreationEntity updateProduct = creationMapper.mapFrom(productDto);
        try {
            service.updateProduct(id, updateProduct);
            ProductCreation dto = creationMapper.mapTo(updateProduct);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Update request failed due to missing fields: {} "+  e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            log.error("Failed to update product: {}"+ e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
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
