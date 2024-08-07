package com.techie.ecommerce.repository;

import com.techie.ecommerce.domain.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
}
