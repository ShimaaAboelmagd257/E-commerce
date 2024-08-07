package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderEntity save(OrderEntity orderEntity);

    Optional<OrderEntity> getOrderById(Long orderId);

    List<OrderEntity> getAllOrders();

    boolean isExists(Long orderId);

    void deleteOrderById(Long orderId);
}
