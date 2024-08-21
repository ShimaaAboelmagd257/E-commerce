package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CategoryEntity;
import com.techie.ecommerce.repository.CategoryRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private static final Log log = LogFactory.getLog(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository repository;
    private RestTemplate restTemplate;
    private final String apiUrl = "https://api.escuelajs.co/api/v1/categories" ;

    public CategoryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        repository.save(category);
          ResponseEntity<CategoryEntity> response = restTemplate.postForEntity(apiUrl, category, CategoryEntity.class);
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            CategoryEntity categoryEntity = response.getBody();

            log.info("API Response: " + categoryEntity);
            if (categoryEntity.getId() == null) {
                log.error("Received null id from API response");
                throw new RuntimeException("Failed to save category to external API");
            }

            log.info("Saved category with Id " + categoryEntity.getId());
            return categoryEntity;
        } else {
            log.error("Failed to save category to external API");
            throw new RuntimeException("Failed to save category to external API");
        }
           // return repository.save(category);
    }

    @Override
    public Page<ProductDto> getProductsByCategory(int page, int size, Integer categoryId) {
        String url = apiUrl + "/" + categoryId + "/products";
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(url, ProductDto[].class);
        List<ProductDto> productDtos = Arrays.asList(response.getBody());
        int start = page * size;
        int end = Math.min((start + size), productDtos.size());
        List<ProductDto> paginatedProducts = productDtos.subList(start, end);

        return new PageImpl<>(paginatedProducts, PageRequest.of(page,size), productDtos.size());
    }

    @Override
    public Page<CategoryDto> fetchAllCategories(int page , int size) {
        ResponseEntity<CategoryDto[]> response = restTemplate.getForEntity(apiUrl, CategoryDto[].class);
        List<CategoryDto> categoryDtos = Arrays.asList(response.getBody());
        return new PageImpl<>(categoryDtos,PageRequest.of(page,size),categoryDtos.size());

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
    public void updateCategory(Integer id, CategoryEntity updateCategory) {
        String updateUrl = apiUrl + "/" + id;
        try {
            restTemplate.put(updateUrl, updateCategory);
            log.info("Category updated remotely with ID: {}"+ id);
        } catch (HttpClientErrorException e) {
            log.error("Error updating Category: {}"+ e.getMessage());
            throw new RuntimeException("Failed to update Category: " + e.getMessage(), e);
        }
    }
}
