package com.techie.ecommerce.product;

import com.techie.ecommerce.domain.model.UserEntity;

import java.util.Collections;

public class UserEntityUtil {

    public static UserEntity createTestUser() {
        return UserEntity.builder()
                .username("shemoii")
                .password("27915555") // Note: In a real application, passwords should be encrypted.
                .email("testuser@example.com")
                .firstName("Test")
                .lastName("User")
                .orders(Collections.emptyList()) // Empty list for simplicity, modify as needed
                .carts(Collections.emptyList())  // Empty list for simplicity, modify as needed
                .build();
    }
}