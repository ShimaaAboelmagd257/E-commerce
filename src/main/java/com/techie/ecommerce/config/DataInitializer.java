package com.techie.ecommerce.config;

import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInitializer {

   /* @Bean
    public CommandLineRunner initializeData(UserService userService) {
        return args -> {
            UserEntity user = new UserEntity();
            user.setUsername("shemo");
            user.setPassword("279155");
            user.setEmail("testuser@example.com");
            user.setFirstName("Shimaa");
            user.setLastName("Aboelmagd");
            user .setOrders(Collections.emptyList()); // Empty list for simplicity, modify as needed
            user.setCarts(Collections.emptyList());
            userService.save(user);
        };
    }*/
}