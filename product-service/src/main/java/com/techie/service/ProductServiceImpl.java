package com.techie.service;

import com.techie.domain.*;
import com.techie.repository.ProductCreateRepository;
import com.techie.repository.ProductRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Log log = LogFactory.getLog(ProductServiceImpl.class);
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String PRODUCT_REQUEST_TOPIC = "product-request";
    private static final String PRODUCT_RESPONSE_TOPIC = "product-response";



    private static final String TOPIC = "cart-events";
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
/*    public Page<ProductDto> fetchAllProducts(int page, int size) {
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(apiUrl, ProductDto[].class);
        List<ProductDto> products = Arrays.asList(response.getBody());

        // Manually implement pagination
        int start = page * size;
        int end = Math.min((start + size), products.size());
        List<ProductDto> paginatedProducts = products.subList(start, end);

        // Return the paginated data as a Page object
        return new PageImpl<>(paginatedProducts, PageRequest.of(page, size), products.size());
    }*/
   /* @Override
    public Page<ProductDto> fetchAllProducts(int page, int size){
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(apiUrl, ProductDto[].class);
        List<ProductDto> products = Arrays.asList(response.getBody());
        int start = page * size;
        int end = Math.min((start + size), products.size());
        List<ProductDto> paginatedProducts = products.subList(start, end);
        return new PageImpl<>(paginatedProducts, PageRequest.of(page, size), products.size());
    }*/
   @Override
   public Page<ProductDto> fetchAllProducts(int page, int size) {
    ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(apiUrl, ProductDto[].class);
    List<ProductDto> products = Arrays.asList(response.getBody());

    int start = page * size;
    if (start >= products.size()) {
        return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), products.size());
    }

    int end = Math.min((start + size), products.size());
    List<ProductDto> paginatedProducts = products.subList(start, end);
    return new PageImpl<>(paginatedProducts, PageRequest.of(page, size), products.size());
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
    public Page<ProductDto> filterProducts(ProductFilter productFilter , int page, int size) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        if (productFilter.getTitle() != null) {
            uriBuilder.queryParam("title", productFilter.getTitle());
        }
        if (productFilter.getPrice() != null) {
            uriBuilder.queryParam("price", productFilter.getPrice());
        }
        if (productFilter.getPrice_min()!= null && productFilter.getPrice_max() != null) {
            uriBuilder.queryParam("price_min", productFilter.getPrice_min());
            uriBuilder.queryParam("price_max", productFilter.getPrice_max());
        }
        if (productFilter.getCategoryId() != null) {
            uriBuilder.queryParam("categoryId", productFilter.getCategoryId());
        }
/*
        if (productFilter.getLimit() != null) {
            uriBuilder.queryParam("limit", productFilter.getLimit());
        }
        if (productFilter.getOffset() != null) {
            uriBuilder.queryParam("offset", productFilter.getOffset());
        }
*/
        uriBuilder.queryParam("page", page);
        uriBuilder.queryParam("size", size);
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(uriBuilder.toUriString(), ProductDto[].class);
        List<ProductDto> productDtos = Arrays.asList(response.getBody());

        return new PageImpl<>(productDtos,PageRequest.of(page,size),200);
    }

    @KafkaListener(topics = PRODUCT_REQUEST_TOPIC, groupId = "product-group")
    public void handleProductRequest(int productId) {
        ProductDto product = fetchProductById(productId);
         ProductResponse productResponse = new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getPrice()
        );
        kafkaTemplate.send(PRODUCT_RESPONSE_TOPIC, productResponse);
    }


/*
GET /products?title=Generic
GET /products?price_min=100&price_max=500
GET /products?categoryId=1&limit=10&offset=0
*/
}
