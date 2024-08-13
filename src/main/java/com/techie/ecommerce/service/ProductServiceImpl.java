package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.ProductEntity;
import com.techie.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;
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
    public ProductDto fetchProductById(Long id) {
        String idUrl = apiUrl + "/" + id;
        ResponseEntity<ProductDto> response = restTemplate.getForEntity(idUrl, ProductDto.class);
        return response.getBody();
    }

    @Override
    public ProductEntity save(ProductEntity product) {
       restTemplate.postForEntity(apiUrl,product,ProductDto.class);
        return productRepository.save(product);
    }

    @Override
    public boolean isExists(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
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

    @Override
    public ProductEntity updateProduct(Long id, ProductEntity updateProduct) {
        String updateUrl = apiUrl + "/" + id;
        restTemplate.put(updateUrl, updateProduct);
        return productRepository.save(updateProduct);
    }


/*
GET /products?title=Generic
GET /products?price_min=100&price_max=500
GET /products?categoryId=1&limit=10&offset=0
*/
}
