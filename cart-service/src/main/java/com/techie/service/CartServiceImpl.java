package com.techie.service;

import com.techie.domain.*;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    public final CartEventPublisher cartEventPublisher;
    public CartEventListener cartEventListener;
    @Autowired
    public CartServiceImpl(CartEventPublisher cartEventPublisher, CartRepository cartRepository) {
        this.cartEventPublisher = cartEventPublisher;
        this.cartRepository = cartRepository;
    }
    private ProductResponceMapperImpl mapper;


    private final CartRepository cartRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final Map<Integer, CompletableFuture<ProductResponse>> productFutureMap = new ConcurrentHashMap<>();

    @Value("${spring.kafka.topic.product-request}")
    private String productRequestTopic;

    @Override
    public CartEntity save(CartEntity cartEntity) {
        if (cartEntity.getCartId() != null && cartRepository.existsById(cartEntity.getCartId())) {
            throw new IllegalArgumentException("This cart id '" + cartEntity.getCartId() + "' is already taken.");
        }
        return cartRepository.save(cartEntity);
    }

    @Override
    public Optional<CartEntity> findById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public void addToCart(Long cartId, Integer productId) {
        cartEventPublisher.requestProductInfo(cartId,productId);
    }


    @Override
    public void delete(Long cartId, Integer productId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

            List<CartItemEntity> cartItemEntities = cart.getCartItems();
            cartItemEntities.removeIf(cartItem ->
                    cartItem.getProduct().getProductId().equals(productId));
            cartRepository.save(cart);

    }

    @Override
    public Optional<CartItemEntity> getCartItem(Long cartId, Integer productId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));
            return cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getProductId().equals(productId))
                    .findFirst();

    }

    @Override
    public List<CartItemEntity> getCartItems(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

            return cart.getCartItems();

    }

    @Override
    public void removeAllCartItems(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

            cart.getCartItems().clear();
            cartRepository.save(cart);
    }

    @Override
    public double getCartTotalPrice(Long cartId) {

        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

        return cart.getCartItems().stream()
                .mapToDouble(cartItem -> {
                    ProductEntity entity = cartItem.getProduct();
                    return entity.getProductPrice() * cartItem.getQuantity();
                })
                .sum();


    }


}
