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
public class OrderDto {
    private Long orderId;
   // private UserEntity user;
    private List<OrderItemDto> orderItems;
    private Double totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
