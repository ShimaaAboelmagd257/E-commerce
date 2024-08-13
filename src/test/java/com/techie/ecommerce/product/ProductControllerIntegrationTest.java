package com.techie.ecommerce.product;

import com.techie.ecommerce.controller.JwtAuthenticationResponse;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.repository.UserRepository;
import com.techie.ecommerce.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductControllerIntegrationTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;
    private String jwtToken;

 /*   private String getJwtToken() {
        // Assuming you have a login endpoint that returns a JWT token
        String loginUrl = "/api/auth/login";
        LoginRequest loginRequest = new LoginRequest("username", "password");
        ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(loginUrl, loginRequest, JwtAuthenticationResponse.class);
        return response.getBody().getAccessToken();
    }*/
    @BeforeEach
    public void setUp() {
        // Create and save a test user
        UserEntity testUser = UserEntityUtil.createTestUser();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));
        if(!userRepository.existsByUsername(testUser.getUsername())){
            userRepository.save(testUser);
        }
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(); // Ensure this is properly configured
        jwtToken = jwtTokenProvider.generateToken(testUser.getUsername());
    }
    @Test
    public void getAllProductsTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductDto[]> response = restTemplate.exchange(
                "/api/products",
                HttpMethod.GET,
                entity,
                ProductDto[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }
    /*@Test
    public void getAllProductsTest(){
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity("/api/products", ProductDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }*/

}