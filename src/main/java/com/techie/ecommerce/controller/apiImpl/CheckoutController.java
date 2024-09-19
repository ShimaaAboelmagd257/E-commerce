package com.techie.ecommerce.controller.apiImpl;

import com.techie.ecommerce.controller.api.CheckoutApi;
import com.techie.ecommerce.domain.dto.PaymentRequest;
import com.techie.ecommerce.domain.model.OrderEntity;
import com.techie.ecommerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/checkout")
public class CheckoutController implements CheckoutApi {
    @Autowired
    private CheckoutService checkoutService;

    @Override
    @PostMapping("/{cartId}")
    public ResponseEntity<OrderEntity> checkout(Long cartId, PaymentRequest paymentRequest) {
       try {
         OrderEntity order =   checkoutService.checkout(cartId,paymentRequest);
         return ResponseEntity.ok(order);
       }catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
    }
}
