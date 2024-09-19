package com.techie.ecommerce.controller.api;

import com.techie.ecommerce.domain.dto.PaymentRequest;
import com.techie.ecommerce.domain.model.OrderEntity;
import org.springframework.http.ResponseEntity;

public interface CheckoutApi {
    ResponseEntity<OrderEntity> checkout(Long cartId , PaymentRequest paymentRequest);
}
