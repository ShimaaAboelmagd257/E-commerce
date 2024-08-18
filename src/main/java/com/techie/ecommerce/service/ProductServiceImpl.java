package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.ProductCreationEntity;
import com.techie.ecommerce.repository.ProductCreateRepository;
import com.techie.ecommerce.repository.ProductRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Log log = LogFactory.getLog(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCreateRepository repository;
    private RestTemplate restTemplate;
    private final String apiUrl = "https://api.escuelajs.co/api/v1/products";



    @Autowired
    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProductDto> fetchAllproducts(){
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(apiUrl, ProductDto[].class);
        return Arrays.asList(response.getBody());
    }
    @Override
    public ProductDto fetchProductById(Integer id) {
        String idUrl = apiUrl + "/" + id;
        ResponseEntity<ProductDto> response = restTemplate.getForEntity(idUrl, ProductDto.class);
        return response.getBody();
    }

    @Transactional
    @Override
    public ProductCreationEntity createProduct(ProductCreationEntity product) {
        ResponseEntity<ProductCreationEntity> response = restTemplate.postForEntity(apiUrl, product, ProductCreationEntity.class);
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            ProductCreationEntity createdProduct = response.getBody();

            log.info("API Response: " + createdProduct);
            if (createdProduct.getId() == null) {
                log.error("Received null id from API response");
                throw new RuntimeException("Failed to save product to external API");
            }

            log.info("Saved product with Id " + createdProduct.getId());
            return createdProduct;
        } else {
            log.error("Failed to save product to external API");
            throw new RuntimeException("Failed to save product to external API");
        }
    }
    @Override
    public void updateProduct(Integer id, ProductCreationEntity updateProduct) {
        log.info("Attempting to update product with ID: {}"+ id);

        String updateUrl = apiUrl + "/" + id;

        // Ensure all necessary fields are set to avoid HTTP 400 errors
        /*if (updateProduct.getDescription() == null) {
            log.error("Description is null for product update with ID: {}"+ id);
            throw new IllegalArgumentException("Description must not be null");
        }*/

        try {
            restTemplate.put(updateUrl, updateProduct);
            log.info("Product updated remotely with ID: {}"+ id);
        } catch (HttpClientErrorException e) {
            log.error("Error updating product: {}"+ e.getMessage());
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }

   /* @Override
    public ProductEntity save(ProductEntity product) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new IllegalArgumentException("Product category ID must be provided");
        }
       restTemplate.postForEntity(apiUrl,product,ProductDto.class);
        return productRepository.save(product);
    }*/

    @Override
    public boolean isExists(Integer id) {
        return productRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
        String deleteUrl = apiUrl + "/" + id;
        restTemplate.delete(deleteUrl);
    }

    @Override
    public List<ProductDto> filterProducts(String title, Double price, Double priceMin, Double priceMax, Long categoryId, Integer limit, Integer offset) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        if (title != null) {
            uriBuilder.queryParam("title", title);
        }
        if (price != null) {
            uriBuilder.queryParam("price", price);
        }
        if (priceMin != null && priceMax != null) {
            uriBuilder.queryParam("price_min", priceMin);
            uriBuilder.queryParam("price_max", priceMax);
        }
        if (categoryId != null) {
            uriBuilder.queryParam("categoryId", categoryId);
        }
        if (limit != null) {
            uriBuilder.queryParam("limit", limit);
        }
        if (offset != null) {
            uriBuilder.queryParam("offset", offset);
        }
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(uriBuilder.toUriString(), ProductDto[].class);
        return Arrays.asList(response.getBody());
    }



/*
GET /products?title=Generic
GET /products?price_min=100&price_max=500
GET /products?categoryId=1&limit=10&offset=0
*/
}
