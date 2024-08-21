package com.techie.ecommerce.controller.apiImpl;

import com.techie.ecommerce.controller.api.ProductApi;
import com.techie.ecommerce.domain.dto.ProductCreation;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.dto.ProductFilter;
import com.techie.ecommerce.domain.model.ProductCreationEntity;
import com.techie.ecommerce.domain.model.ProductEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController implements ProductApi {
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

    @Override
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductCreation creation){
        ProductCreationEntity product = creationMapper.mapFrom(creation);
        ProductCreationEntity savedCreation = service.createProduct(product);
        ProductCreation dto = creationMapper.mapTo(savedCreation);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public Page<ProductDto> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return service.fetchAllProducts( page,size);
    }



    @Override
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id){
        return service.fetchProductById(id);
    }
    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductCreation productDto){
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
    @Override

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @Override
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> filterProducts(
            @RequestParam ProductFilter productFilter ,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ProductDto> products = service.filterProducts(productFilter, page, size);
        return ResponseEntity.ok(products);
    }
}
