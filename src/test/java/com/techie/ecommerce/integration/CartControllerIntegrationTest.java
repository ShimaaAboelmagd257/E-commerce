package com.techie.ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techie.ecommerce.controller.apiImpl.AuthController;
import com.techie.ecommerce.controller.apiImpl.CartController;
import com.techie.ecommerce.domain.dto.CartDto;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.mappers.CartMapperImpl;
import com.techie.ecommerce.repository.CartRepository;
import com.techie.ecommerce.repository.UserRepository;
import com.techie.ecommerce.security.JwtTokenProvider;
import com.techie.ecommerce.service.CartService;
import com.techie.ecommerce.testUtl.UserEntityUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.techie.ecommerce.testUtl.CartDtoUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CartControllerIntegrationTest {

    private static final Log log = LogFactory.getLog(CartControllerIntegrationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;
    private String jwtToken;
    @Mock
    private CartService cartService;

    @Mock
    private CartMapperImpl cartMapper;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setUp() {
        String uniqueUsername = "user_" + UUID.randomUUID();
        String uniqueEmail = uniqueUsername + "@example.com";

        UserEntity testUser = UserEntityUtil.createUserEntity();
        testUser.setUsername(uniqueUsername);  // Use unique username
        testUser.setEmail(uniqueEmail);  // Use unique email
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));

        if (!userRepository.existsByUsername(testUser.getUsername())) {
            userRepository.save(testUser);
        }

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        jwtToken = jwtTokenProvider.generateToken(testUser.getUsername());
    }

    @Test
    void CreateCartTest() throws Exception{
        CartDto cartDto = createCartDto();  // Helper method to create CartDto
        CartEntity cartEntity = createCartEntity();  // Helper method to create CartEntity
        CartEntity savedCartEntity = createSavedCartEntity();  // Simulated saved cart entity after calling the service

        // Mocking the behavior for cartMapper and cartService
        when(cartMapper.mapFrom(cartDto)).thenReturn(cartEntity);  // Simulate mapping CartDto to CartEntity
        when(cartService.save(any(CartEntity.class))).thenReturn(savedCartEntity);  // Simulate saving the cart entity
        when(cartMapper.mapTo(savedCartEntity)).thenReturn(cartDto);  // Simulate mapping the saved entity back to CartDto
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("Generated CartDto createdAt: "+ cartDto.getCreatedAt());

        String cartJson = objectMapper.writeValueAsString(cartDto);
        ResultActions resultActions = mockMvc.perform(post("/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cartJson));
        log.info("------------------ResultActions------------ ");

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartId").exists())
                .andDo(print());
    }
}
