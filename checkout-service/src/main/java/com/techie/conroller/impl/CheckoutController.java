package com.techie.conroller.impl;

import com.techie.conroller.api.CheckoutApi;
import com.techie.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/checkout")
public class CheckoutController implements CheckoutApi {
    @Autowired
    private CheckoutService checkoutService;

  /*  @Override
    @PostMapping("/{cartId}")
    public ResponseEntity<OrderEntity> checkout(Long cartId, PaymentRequest paymentRequest) {
       try {
         OrderEntity order =   checkoutService.checkout(cartId,paymentRequest);
         return ResponseEntity.ok(order);
       }catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
    }*/
}
