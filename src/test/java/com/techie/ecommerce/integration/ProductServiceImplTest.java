package com.techie.ecommerce.integration;


import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.service.ProductServiceImpl;
import com.techie.ecommerce.testUtl.ProductDtoUtil;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductServiceImplTest {

    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    private ProductServiceImpl productService;
    private final String apiUrl = "https://api.escuelajs.co/api/v1/products";

   /* @Test
    public void testFetchAllProducts() {
        ProductDto mockProduct = ProductDtoUtil.createSampleProductDto();
        ProductDto[] mockProductsArray = new ProductDto[]{mockProduct};
        ResponseEntity<ProductDto[]> responseEntity = new ResponseEntity<>(mockProductsArray, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(apiUrl + "?page=0&size=10", ProductDto[].class)).thenReturn(responseEntity);
        Page<ProductDto> productDtos = productService.fetchAllProducts(0, 10);
        assertEquals(1, productDtos.getTotalElements());
        assertEquals(mockProduct.getId(), productDtos.getContent().get(0).getId());
    }*/
   @Test
   public void testFetchAllProducts() {
       // Arrange
       ProductDto mockProduct1 = ProductDtoUtil.createSampleProductDto();
       ProductDto mockProduct2 = ProductDtoUtil.createSampleProductDto();
       mockProduct2.setId(2);

       ProductDto[] mockProductsArray = new ProductDto[]{mockProduct1, mockProduct2};
       ResponseEntity<ProductDto[]> responseEntity = new ResponseEntity<>(mockProductsArray, HttpStatus.OK);
       Mockito.when(restTemplate.getForEntity(apiUrl, ProductDto[].class)).thenReturn(responseEntity);
       Page<ProductDto> productDtos = productService.fetchAllProducts(0, 1);

       assertNotNull(productDtos);
       assertEquals(2, productDtos.getTotalElements());
       assertEquals(1, productDtos.getContent().size());
       assertEquals(mockProduct1.getId(), productDtos.getContent().get(0).getId());
   }

    @Test
    public void testFetchAllProductsWithEmptyPage() {
        // Arrange
        ProductDto[] mockProductsArray = new ProductDto[]{};
        ResponseEntity<ProductDto[]> responseEntity = new ResponseEntity<>(mockProductsArray, HttpStatus.OK);

        // Mocking the restTemplate response
        Mockito.when(restTemplate.getForEntity(apiUrl, ProductDto[].class)).thenReturn(responseEntity);

        // Act
        Page<ProductDto> productDtos = productService.fetchAllProducts(1, 10); // Page number greater than available

        assertNotNull(productDtos);
        assertEquals(0, productDtos.getTotalElements()); // Total number of elements
        assertEquals(0, productDtos.getContent().size()); // Number of elements on the current page
    }

    @Test
    public void testFetchProductsById(){
        Integer productId = 1;
        ProductDto mockProducts = ProductDtoUtil.createSampleProductDto();
        ResponseEntity<ProductDto> responseEntity = new ResponseEntity<>(mockProducts, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(apiUrl + "/" + productId,ProductDto.class)).thenReturn(responseEntity);
        ProductDto productDto = productService.fetchProductById(productId);
        assertNotNull(productDto);
        assertEquals("Sample Product",productDto.getTitle());
    }

}
