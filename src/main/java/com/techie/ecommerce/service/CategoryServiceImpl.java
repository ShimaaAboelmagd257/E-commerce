package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CategoryEntity;
import com.techie.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository repository;
    private RestTemplate restTemplate;
    private final String apiUrl = "https://api.escuelajs.co/api/v1/categories" ;

    public CategoryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return repository.save(category);
    }

    @Override
    public List<ProductDto> getProductsByCategory(Integer categoryId) {
        String url = apiUrl + "/" + categoryId + "/products";
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(url, ProductDto[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public List<CategoryDto> fetchAllCategories() {
        ResponseEntity<CategoryDto[]> response = restTemplate.getForEntity(apiUrl, CategoryDto[].class);
        return Arrays.asList(response.getBody());

    }

    @Override
    public CategoryDto fetchCategoryById(Integer id) {
         String idUrl = apiUrl + "/" + id;
        ResponseEntity<CategoryDto> response = restTemplate.getForEntity(idUrl, CategoryDto.class);
        return response.getBody();
    }

    @Override
    public boolean isExists(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
        String deleteUrl = apiUrl + "/" + id;
        restTemplate.delete(deleteUrl);
    }

    @Override
    public CategoryEntity updateCategory(Integer id, CategoryEntity updateCategory) {
        String updateUrl = apiUrl + "/" + id;
        restTemplate.put(updateUrl, updateCategory);
        return repository.save(updateCategory);
    }
}
