package com.techie.ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techie.ecommerce.domain.dto.UserDto;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.repository.UserRepository;
import com.techie.ecommerce.security.JwtTokenProvider;
import com.techie.ecommerce.testUtl.UserEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    private String jwtToken;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


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
    @WithMockUser
    void createUserTest() throws  Exception{
        UserDto userDto = UserEntityUtil.createUserDtoUtil();
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    void getUserByIdTest() throws Exception{
        Long userId = 1L;
        mockMvc.perform(get("/user/{userId}",userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    @WithMockUser
    void getAllUsers() throws Exception{
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser
    void updateUserTest() throws Exception{
        Long userId = 1L;
        UserDto userDto = UserEntityUtil.createUserDtoUtil();
        userDto.setUsername("Updated");
        mockMvc.perform(put("/user/{userId}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Updated"));
    }

    @Test
    @WithMockUser
    void deleteUserTest() throws Exception{
        Long userId = 1L;
        mockMvc.perform(delete("/user/{userId}",userId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void getUserByUsernameOrEmail() throws Exception{
      //  UserDto userDto = UserEntityUtil.createUserDtoUtil();
        String username = "shosh";
        String email = "shosh@gmail.com";
        mockMvc.perform(get("/user/search")
                .param("username",username)
                .param("email",email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    @WithMockUser
    void changePasswordTest() throws Exception{
        Long userId = 1L;
        mockMvc.perform(put("/user/{userId}/change-password",userId)
                .param("newPassword","newPassword"))
                .andExpect(status().isOk());

    }

}
