package com.techie.ecommerce.controller.apiImpl;

import com.techie.ecommerce.controller.api.CategoryApi;
import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.dto.PageResponse;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CategoryEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController implements CategoryApi {

    private final Mapper<CategoryEntity, CategoryDto> mapper;
    private final CategoryService service;

    public CategoryController(Mapper<CategoryEntity, CategoryDto> mapper, CategoryService service) {
        this.mapper = mapper;
        this.service = service;
    }


    @Override
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryEntity category = mapper.mapFrom(categoryDto);
        CategoryEntity savedCategory = service.createCategory(category);
        CategoryDto dto = mapper.mapTo(savedCategory);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @Override
    @GetMapping("/{id}/products")
    public ResponseEntity<Page<ProductDto>> getProductsByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable("id")  Integer id)
    {
        Page<ProductDto> products = service.getProductsByCategory(page,size,id);
        return ResponseEntity.ok(products);
    }
    @Override
    @GetMapping
    public PageResponse<CategoryDto> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

            ){
        Page<CategoryDto> categoryDtos = service.fetchAllCategories(page, size);
        PageResponse<CategoryDto> response = new PageResponse<>();
        response.setContent(categoryDtos.getContent());
        response.setTotalPages(categoryDtos.getTotalPages());
        response.setTotalElements(categoryDtos.getTotalElements());
        return response;

    }
    @Override
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable("id")  Integer id){
        return service.fetchCategoryById(id);
    }
    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id")  Integer id , @RequestBody CategoryDto categoryDto){
       /* if(!service.isExists(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }*/
        categoryDto.setId(id);
        CategoryEntity updateCategory = mapper.mapFrom(categoryDto);
         service.updateCategory(id, updateCategory);
        CategoryDto dto = mapper.mapTo(updateCategory);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
