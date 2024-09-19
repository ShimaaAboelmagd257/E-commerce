package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.PaymentRequest;
import com.techie.ecommerce.domain.model.OrderEntity;

public interface CheckoutService {
    OrderEntity checkout(Long cartId, PaymentRequest paymentRequest);
}
