package com.techie.ecommerce.domain.dto;

import com.techie.ecommerce.domain.model.OrderEntity;
import com.techie.ecommerce.domain.model.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;
    private OrderEntity order;
    private ProductEntity product;
    private int quantity;
}
