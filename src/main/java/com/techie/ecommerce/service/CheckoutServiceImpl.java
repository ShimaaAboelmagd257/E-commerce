package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.PaymentRequest;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.OrderEntity;
import com.techie.ecommerce.domain.model.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService{
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;

    @Override
    public OrderEntity checkout(Long cartId, PaymentRequest paymentRequest) {
       if (!cartService.checkInventoryBeforeCheckOut(cartId)){
           throw new RuntimeException("one or more items out of stock !");
       }
        CartEntity cart = cartService.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart Not found"));
       OrderEntity order = createOrderFromCart(cart);
     //  boolean paymentSucces = paymentService;

               return orderService.save(order);

    }

    private OrderEntity createOrderFromCart(CartEntity cart){
        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());
        order.setOrderItems(createOrderItemsFromCart(cart));
        order.setTotalPrice(cart.getTotalPrice());
        return order;

    }
    private List<OrderItemEntity> createOrderItemsFromCart(CartEntity cart){
        return cart.getCartItems().stream()
                .map(cartItem -> new OrderItemEntity(cartItem.getProduct(),cartItem.getQuantity(),cartItem.getProduct().getPrice()))
                .collect(Collectors.toList());
    }

}
