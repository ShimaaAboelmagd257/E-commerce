package com.techie.ecommerce.domain.dto;

import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.OrderItemEntity;
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
public class OrderDto {
    private Long orderId;
    private UserEntity user;
    private List<OrderItemDto> orderItems;
    private Double totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
