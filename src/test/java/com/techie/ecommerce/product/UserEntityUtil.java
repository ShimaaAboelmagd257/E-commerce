package com.techie.ecommerce.product;

import com.techie.ecommerce.domain.model.UserEntity;

import java.util.Collections;

public class UserEntityUtil {

    public static UserEntity createTestUser() {
        return UserEntity.builder()
                .username("shemoii")
                .password("279155")
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .orders(Collections.emptyList())
                .carts(Collections.emptyList())  
                .build();
    }
}