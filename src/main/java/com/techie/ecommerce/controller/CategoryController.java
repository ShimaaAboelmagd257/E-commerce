package com.techie.ecommerce.controller;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CategoryEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final Mapper<CategoryEntity, CategoryDto> mapper;
    private final CategoryService service;

    public CategoryController(Mapper<CategoryEntity, CategoryDto> mapper, CategoryService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryEntity category = mapper.mapFrom(categoryDto);
        CategoryEntity savedCategory = service.createCategory(category);
        CategoryDto dto = mapper.mapTo(savedCategory);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("id")  Integer id) {
        List<ProductDto> products = service.getProductsByCategory(id);
        return ResponseEntity.ok(products);
    }
    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return service.fetchAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable("id")  Integer id){
        return service.fetchCategoryById(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id")  Integer id , @RequestBody CategoryDto categoryDto){
        if(!service.isExists(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        categoryDto.setId(id);
        CategoryEntity updateCategory = mapper.mapFrom(categoryDto);
        CategoryEntity category = service.updateCategory(id, updateCategory);
        CategoryDto dto = mapper.mapTo(category);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id")  Integer id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
