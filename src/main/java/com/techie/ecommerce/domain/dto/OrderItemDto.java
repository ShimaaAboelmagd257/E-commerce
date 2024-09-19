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
    private OrderDto order;
    private ProductDto product;
    private int quantity;
    private double price;
}
