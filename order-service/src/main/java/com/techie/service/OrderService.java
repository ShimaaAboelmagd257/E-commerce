package com.techie.service;

import com.techie.domain.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderEntity save(OrderEntity orderEntity);

    Optional<OrderEntity> getOrderById(Long orderId);

    List<OrderEntity> getAllOrders();

    boolean isExists(Long orderId);

    void deleteOrderById(Long orderId);
}
