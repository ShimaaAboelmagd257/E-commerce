package com.techie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String requestToken;

    private String firstName;
    private String lastName;
    /*private List<OrderEntity> orders;
    private List<CartEntity> carts;*/
}
