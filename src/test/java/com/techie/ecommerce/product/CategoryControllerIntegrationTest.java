package com.techie.ecommerce.product;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.repository.UserRepository;
import com.techie.ecommerce.security.JwtTokenProvider;
import com.techie.ecommerce.testUtl.CategoryDtoUtil;
import com.techie.ecommerce.testUtl.UserEntityUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class CategoryControllerIntegrationTest {
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
        UserEntity testUser = UserEntityUtil.createTestUser();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));
        if(!userRepository.existsByUsername(testUser.getUsername())){
            userRepository.save(testUser);
        }
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(); // Ensure this is properly configured
        jwtToken = jwtTokenProvider.generateToken(testUser.getUsername());
    }
    @Test
    public void getAllCategoriesTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CategoryDto[]> response = restTemplate.exchange(
                "/api/categories",
                HttpMethod.GET,
                entity,
                CategoryDto[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }
       @Test
    void createCategoryTest(){
        CategoryDto creation  = CategoryDtoUtil.createCategory();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<CategoryDto> entity = new HttpEntity<>(creation,headers);

        ResponseEntity<CategoryDto> response = restTemplate.exchange(
                "/api/categories",
                HttpMethod.POST,
                entity,
                CategoryDto.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void updateCategoryTest() {

        CategoryDto category  = CategoryDtoUtil.createCategory();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<CategoryDto> entity = new HttpEntity<>(category, headers);
        ResponseEntity<CategoryDto> responseCreation  = restTemplate.exchange(
                "/api/categories",
                HttpMethod.POST,
                entity,
                CategoryDto.class
        );

        CategoryDto updateRequest = CategoryDto.builder()
                .name("Updated Cat")
                .image("https://i.imgur.com/UlxxXyG.jpeg")
               .build();
        log.info("Sending PUT request to update Category ID: " + responseCreation.getBody().getId());
        log.info("Update request body: " + updateRequest);


        HttpEntity<CategoryDto> updateEntity = new HttpEntity<>(updateRequest, headers);
        ResponseEntity<CategoryDto> response = restTemplate.exchange(
                "/api/categories/" + responseCreation.getBody().getId(),
                HttpMethod.PUT,
                updateEntity,
                CategoryDto.class
        );
        log.info("Response from PUT update: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Updated Cat");
        assertThat(response.getBody().getImage()).isEqualTo("https://i.imgur.com/UlxxXyG.jpeg");
    }

    @Test
    void getCategoryByIdTest(){
        CategoryDto category  = CategoryDtoUtil.createCategory();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<CategoryDto> entity1 = new HttpEntity<>(category,headers);

        ResponseEntity<CategoryDto> createResponse = restTemplate.exchange(
                "/api/categories",
                HttpMethod.POST,
                entity1,
                CategoryDto.class
        );
        Integer createdCategoryId = createResponse.getBody().getId();
        assertNotNull(createdCategoryId);

        HttpEntity<String> entity2 = new HttpEntity<>(headers);

        ResponseEntity<CategoryDto> response2 = restTemplate.exchange(
                "/api/categories/"+ createdCategoryId,
                HttpMethod.GET,
                entity2,
                CategoryDto.class
        );

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertTrue(response2.getBody().getId() == createdCategoryId);
    }
    @Test
    void deletProductByIdTest(){
        CategoryDto category  = CategoryDtoUtil.createCategory();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<CategoryDto> entity1 = new HttpEntity<>(category,headers);

        ResponseEntity<CategoryDto> createResponse = restTemplate.exchange(
                "/api/categories",
                HttpMethod.POST,
                entity1,
                CategoryDto.class
        );
        Integer createdCategoryId = createResponse.getBody().getId();
        assertNotNull(createdCategoryId);

        HttpEntity<String> deleteRequest = new HttpEntity<>(headers);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/categories/" + createdCategoryId,
                HttpMethod.DELETE,
                deleteRequest,
                Void.class
        );
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode(), "Delete response status should be NO_CONTENT");

    }

}
