package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.OrderEntity;
import com.techie.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements  OrderService{

    @Autowired
    private OrderRepository repository;

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        return repository.save(orderEntity);
    }

    @Override
    public Optional<OrderEntity> getOrderById(Long orderId) {
        return repository.findById(orderId);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public boolean isExists(Long orderId) {
        return repository.existsById(orderId);
    }

    @Override
    public void deleteOrderById(Long orderId) {
        repository.deleteById(orderId);
    }
}
