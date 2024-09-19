package com.techie.ecommerce.domain.dto;

import com.techie.ecommerce.domain.model.CartItemEntity;
import com.techie.ecommerce.domain.model.ProductEntity;
import com.techie.ecommerce.domain.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartDto {
    private Long cartId;
    private Long userId;
    private List<CartItemDto> cartItems;
    private double totalPrice;
    private LocalDateTime createdAt;
}
