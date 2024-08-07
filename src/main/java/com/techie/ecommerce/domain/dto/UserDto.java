package com.techie.ecommerce.domain.dto;

import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.OrderEntity;
import jakarta.persistence.*;
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
    private String firstName;
    private String lastName;
    private List<CartEntity> carts;
    private List<OrderEntity> orders;
}
