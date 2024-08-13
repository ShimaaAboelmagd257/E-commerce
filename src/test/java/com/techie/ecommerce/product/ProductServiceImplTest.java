package com.techie.ecommerce.product;


import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.service.ProductServiceImpl;
import com.techie.ecommerce.testUtl.ProductDtoUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductServiceImplTest {

    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    private ProductServiceImpl productService;
    private final String apiUrl = "https://api.escuelajs.co/api/v1/products";

    @Test
    public void testFetchAllProducts(){
        ProductDto[] mockProducts = new ProductDto[]{ProductDtoUtil.createSampleProductDto()};
        ResponseEntity<ProductDto[]> responseEntity = new ResponseEntity<>(mockProducts, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(apiUrl,ProductDto[].class)).thenReturn(responseEntity);
        List<ProductDto> productDtos = productService.fetchAllproducts();
        assertEquals(1,productDtos.size());
        assertEquals("Sample Product",productDtos.get(0).getTitle());
     }

    @Test
    public void testFetchProductsById(){
        Long productId = 1L;
        ProductDto mockProducts = ProductDtoUtil.createSampleProductDto();
        ResponseEntity<ProductDto> responseEntity = new ResponseEntity<>(mockProducts, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(apiUrl + "/" + productId,ProductDto.class)).thenReturn(responseEntity);
        ProductDto productDto = productService.fetchProductById(productId);
        assertNotNull(productDto);
        assertEquals("Sample Product",productDto.getTitle());
    }

}
