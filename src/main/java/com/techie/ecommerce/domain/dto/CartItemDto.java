package com.techie.ecommerce.domain.dto;

import com.techie.ecommerce.domain.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartItemDto {
    private Long id;
    private Long productId;
    private int quantity;
    private double price;
}
