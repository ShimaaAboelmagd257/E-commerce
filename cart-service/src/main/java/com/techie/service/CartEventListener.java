package com.techie.service;


import com.techie.domain.CartEntity;
import com.techie.domain.CartItemEntity;
import com.techie.domain.ProductEntity;
import com.techie.domain.ProductResponse;
import com.techie.mappers.ProductResponceMapperImpl;
import com.techie.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private ProductResponceMapperImpl mapper;

    @Autowired
    public CartEventListener(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    private final CartRepository cartRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final Map<Integer, CompletableFuture<ProductResponse>> productFutureMap = new ConcurrentHashMap<>();

    @Value("${spring.kafka.topic.product-request}")
    private String productRequestTopic;

    @Value("${spring.kafka.topic.inventory-check}")
    private String inventoryCheckTopic;

    @KafkaListener(topics = "${spring.kafka.topic.product-response}", groupId = "${spring.kafka.consumer.group-id}")
    public CartEntity handleProductResponse(ProductResponse productResponse, Long cartId) {
        logger.info("----------------------Received product response: {}----------------", productResponse);
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        ProductEntity product = mapper.mapFrom(productResponse);

        CartItemEntity existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productResponse.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
        } else {
            CartItemEntity newCartItem = CartItemEntity.builder()
                    .product(product)
                    .cart(cart)
                    .quantity(1)
                    .build();
            cart.getCartItems().add(newCartItem);
        }
        logger.info("----------------------Update Cart with id: {}----------------", cartId);

       return cartRepository.save(cart);


    }

    @KafkaListener(topics = "${spring.kafka.topic.inventory-check}" , groupId = "${spring.kafka.consumer.group-id}")
    public boolean checkInventoryBeforeCheckOut (ProductResponse response , Long cartId) {
        logger.info("------------checkInventoryBeforeCheckOut----------Received product response: {}----------------", response);
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new NotFoundException("Cart not found"));
        for (CartItemEntity item:cart.getCartItems()){
            if (response.getQuantity() < item.getQuantity()){
                logger.warn("Insufficient inventory for product ID: {}", item.getProduct().getProductId());
                return false;
            }
        }
        logger.info("----------------------checkInventoryBeforeCheckOut: true CartId{}----------------", cartId);
        return true;
    }
}
