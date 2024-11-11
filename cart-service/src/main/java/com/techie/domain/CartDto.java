package com.techie.domain;

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
