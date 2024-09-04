package com.techie.ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techie.ecommerce.domain.dto.UserDto;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.mappers.UserMapperImpl;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @Autowired
    private UserMapperImpl userMapper;


   /* @BeforeEach
    public void setUp() {
        // Create and save a test user
        UserEntity testUser = UserEntityUtil.createUserEntity();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));
        if(!userRepository.existsByUsername(testUser.getUsername())){
            userRepository.save(testUser);
        }
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(); // Ensure this is properly configured
        jwtToken = jwtTokenProvider.generateToken(testUser.getUsername());
    }*/

    @Test
    @WithMockUser
    void createUserTest() throws  Exception{
        UserDto testUser = UserEntityUtil.createUserDtoUtil();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));
        /*if(!userRepository.existsByUsername(testUser.getUsername())){
            userRepository.save(testUser);
        }*/
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(); // Ensure this is properly configured
        jwtToken = jwtTokenProvider.generateToken(testUser.getUsername());
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    void getUserByIdTest() throws Exception{
        UserEntity testUser = UserEntityUtil.createUserEntity();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));

       // if(!userRepository.existsById(testUser.getId())){
            userRepository.save(testUser);
     //   }

        Long userId = testUser.getId();
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
        userDto.setUsername("ha");

        mockMvc.perform(put("/user/{userId}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ha"));
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
        String username = "shemoiiii";
        String email = "testuserrr@example.com";
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
        UserEntity testUser = UserEntityUtil.createUserEntity();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));
        userRepository.save(testUser);
        Long userId = testUser.getId();
        mockMvc.perform(put("/user/{userId}/change-password",userId)
                .param("newPassword","newPassword"))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser
    void forgetPasswordTest() throws  Exception{
        UserEntity testUser = UserEntityUtil.createUserEntity();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));
        userRepository.save(testUser);
        String email = testUser.getEmail();
        mockMvc.perform(post("/user/forget-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    void resetPasswordTest() throws Exception{
        UserEntity testUser = UserEntityUtil.createUserEntity();
        testUser.setPassword(new BCryptPasswordEncoder().encode("279155"));
        testUser.setRequestToken("valid-reset-token");
        userRepository.save(testUser);

        String newPassword = "newPassword";
        mockMvc.perform(post("/user/reset-password")
                        .param("token","valid-reset-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newPassword\":\"" + newPassword + "\"}"))
                .andExpect(status().isOk());
        UserEntity updated = userRepository.findById(testUser.getId()).orElseThrow();
        assertTrue(passwordEncoder.matches(newPassword,updated.getPassword()));

    }
    @Test
    @WithMockUser(username = "shbasheb")
    void getProfileTest() throws Exception{
        UserEntity testUser = UserEntityUtil.createUserEntity();
        testUser.setUsername("shbasheb");
        userRepository.save(testUser);
        UserDto dto = userMapper.mapTo(testUser);

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(dto.getUsername()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()));
    }

}
