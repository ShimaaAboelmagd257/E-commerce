package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;
    private RestTemplate restTemplate;
    private final String apiUrl = "https://api.escuelajs.co/api/v1/products" ;
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

}
