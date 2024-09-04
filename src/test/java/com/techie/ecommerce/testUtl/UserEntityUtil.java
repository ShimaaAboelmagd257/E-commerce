package com.techie.ecommerce.testUtl;

import com.techie.ecommerce.domain.dto.UserDto;
import com.techie.ecommerce.domain.model.UserEntity;

import java.util.Collections;

public class UserEntityUtil {

    public static UserEntity createUserEntity() {
        return UserEntity.builder()
                .username("jackimohammed")
                .password("279155")
                .email("testuserrr@example.com")
                .firstName("Test")
                .lastName("User")
                .orders(Collections.emptyList())
                .carts(Collections.emptyList())  
                .build();
    }
    public static UserEntity createUserEntity2() {
        return UserEntity.builder()
                .username("jack")
                .password("279155")
                .email("jack@example.com")
                .firstName("jackson")
                .lastName("Berson")
                .orders(Collections.emptyList())
                .carts(Collections.emptyList())
                .build();
    }
    public static UserDto createUserDtoUtil(){
        return UserDto.builder()
                .username("Shoshosjjhosho")
                .password("279155")
                .email("Shoshiiiiii@example.com")
                .firstName("Test")
                .lastName("User")
                .orders(Collections.emptyList())
                .carts(Collections.emptyList())
                .build();
    }
}