package com.techie.ecommerce.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techie.ecommerce.domain.dto.PageResponse;
import com.techie.ecommerce.domain.dto.ProductCreation;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.dto.UpdateProductResponse;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.repository.UserRepository;
import com.techie.ecommerce.security.JwtTokenProvider;
import com.techie.ecommerce.testUtl.ProductDtoUtil;
import com.techie.ecommerce.testUtl.UserEntityUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductControllerIntegrationTest {
    private static final Log log = LogFactory.getLog(ProductControllerIntegrationTest.class);

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;
    private String jwtToken;


    @BeforeEach
    public void setUp() {
        // Create and save a test user
        UserEntity testUser = UserEntityUtil.createUserEntity();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));
        if(!userRepository.existsByUsername(testUser.getUsername())){
            userRepository.save(testUser);
        }
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(); // Ensure this is properly configured
        jwtToken = jwtTokenProvider.generateToken(testUser.getUsername());
    }
    @Test
    public void getAllProductsTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/products?page=0&size=10",
                HttpMethod.GET,
                entity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectMapper mapper = new ObjectMapper();
        PageResponse<ProductDto> page = mapper.readValue(response.getBody(), new TypeReference<PageResponse<ProductDto>>() {});
        assertNotNull(page);
        assertTrue(page.getContent().size() > 0);
    }

    @Test
    void createPtoductTest(){
        ProductCreation creation  = ProductDtoUtil.creation();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<ProductCreation> entity = new HttpEntity<>(creation,headers);

        ResponseEntity<ProductCreation> response = restTemplate.exchange(
                "/api/products",
                HttpMethod.POST,
                entity,
                ProductCreation.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }
    @Test
    void updateProductTest() {

        ProductCreation product = ProductDtoUtil.creation();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<ProductCreation> entity = new HttpEntity<>(product, headers);
        ResponseEntity<ProductCreation> responseCreation  = restTemplate.exchange(
                "/api/products",
                HttpMethod.POST,
                entity,
                ProductCreation.class
        );

        ProductCreation updateRequest = ProductCreation.builder()
                .title("Updated Title")
                .price(150.0)
                .description("new Desc")
                .images(
                        Arrays.asList(
                                "https://i.imgur.com/QkIa5tT.jpeg",
                                "https://i.imgur.com/jb5Yu0h.jpeg",
                                "https://i.imgur.com/UlxxXyG.jpeg"
                        )
                ).build();
        log.info("Sending PUT request to update product ID: " + responseCreation.getBody().getId());
        log.info("Update request body: " + updateRequest);
        HttpEntity<ProductCreation> updateEntity = new HttpEntity<>(updateRequest, headers);
        ResponseEntity<UpdateProductResponse> response = restTemplate.exchange(
                "/api/products/" + responseCreation.getBody().getId(),
                HttpMethod.PUT,
                updateEntity,
                UpdateProductResponse.class
        );
        log.info("Response from PUT update: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Updated Title");
        assertThat(response.getBody().getPrice()).isEqualTo(150.0);
    }
    @Test
    void getProductByIdTest(){
        ProductCreation creation  = ProductDtoUtil.creation();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<ProductCreation> entity1 = new HttpEntity<>(creation,headers);

        ResponseEntity<ProductCreation> createResponse = restTemplate.exchange(
                "/api/products",
                HttpMethod.POST,
                entity1,
                ProductCreation.class
        );
        Integer createdProductId = createResponse.getBody().getId();
        assertNotNull(createdProductId);

        HttpEntity<String> entity2 = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response2 = restTemplate.exchange(
                "/api/products/"+ createdProductId ,
                HttpMethod.GET,
                entity2,
                ProductDto.class
        );

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
       // assertTrue(response2.getBody().getId() == createdProductId);
    }

    @Test
    void deletProductByIdTest(){
        ProductCreation creation  = ProductDtoUtil.creation();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<ProductCreation> entity1 = new HttpEntity<>(creation,headers);

        ResponseEntity<ProductCreation> createResponse = restTemplate.exchange(
                "/api/products",
                HttpMethod.POST,
                entity1,
                ProductCreation.class
        );
        Integer createdProductId = createResponse.getBody().getId();
        assertNotNull(createdProductId);

        HttpEntity<String> deleteRequest = new HttpEntity<>(headers);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/products/" + createdProductId,
                HttpMethod.DELETE,
                deleteRequest,
                Void.class
        );
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode(), "Delete response status should be NO_CONTENT");

    }



}