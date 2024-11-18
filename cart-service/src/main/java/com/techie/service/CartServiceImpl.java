package com.techie.service;

import com.techie.domain.CartEntity;
import com.techie.domain.CartItemEntity;
import com.techie.domain.ProductResponse;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final Map<Integer, CompletableFuture<ProductResponse>> productFutureMap = new ConcurrentHashMap<>();

    @Value("${kafka.topic.product-request}")
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
    public void delete(Long cartId, Integer productId) {

    }

    @Override
    public Optional<CartItemEntity> getCartItem(Long cartId, Integer productId) {
        return Optional.empty();
    }

    @Override
    public List<CartItemEntity> getCartItems(Long cartId) {
        return List.of();
    }

    @Override
    public void removeAllCartItems(Long cartId) {

    }

    @Override
    public double getCartTotalPrice(Long cartId) {
        return 0;
    }

    @Override
    public boolean checkInventoryBeforeCheckOut(Long cartId) {
        return false;
    }

    @Override
    public CompletableFuture<CartEntity> requestProductInfo(Long cartId, Integer productId) {
        // Fetch cart by ID
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        // Create and store a CompletableFuture for the product response
        CompletableFuture<ProductResponse> productFuture = new CompletableFuture<>();
        productFutureMap.put(productId, productFuture);

        // Send the product request to Kafka
        kafkaTemplate.send(productRequestTopic, productId);
        logger.info("Sending productId to topic {}: {}", productRequestTopic, productId);

        // Chain the CompletableFuture to process the product response and update the cart
        return productFuture.thenApply(productResponse -> {
            // Check if the product already exists in the cart
            CartItemEntity existingCartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getProductId().equals(productResponse.getProductId()))
                    .findFirst()
                    .orElse(null);

            if (existingCartItem != null) {
                // Increment quantity if the product already exists
                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            } else {
                // Add new product to the cart
                CartItemEntity newCartItem = CartItemEntity.builder()
                        .product(productResponse)
                        .cart(cart)
                        .quantity(1)
                        .build();
                cart.getCartItems().add(newCartItem);
            }

            // Save the updated cart to the repository
            cartRepository.save(cart);

            // Return the updated cart
            return cart;
        }).exceptionally(ex -> {
            // Log the exception
            logger.error("Failed to process product response for cartId {} and productId {}", cartId, productId, ex);

            // Throw a custom exception instead of returning null
            throw new RuntimeException("Failed to update cart", ex);
        });
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.product-response}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleProductResponse(ProductResponse productResponse, Long cartId) {
        logger.info("Received product response: {}", productResponse);
      CompletableFuture<ProductResponse> completableFuture = productFutureMap.remove(productResponse);
      if (completableFuture != null){
          completableFuture.complete(productResponse);
      }else {
          logger.warn("No CompletableFuture found for productId {}", productResponse.getProductId());
      }
    }

  /*  @Override
    public void delete(Long cartId, Integer productId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));

            List<CartItemEntity> cartItemEntities = cart.getCartItems();
            cartItemEntities.removeIf(cartItem ->
                    cartItem.getProduct().getId().equals(productId));
            cartRepository.save(cart);

    }*/

/*    @Override
    public Optional<CartItemEntity> getCartItem(Long cartId, Integer productId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new  ResourceNotFoundException( "Cart Not Found"));
            return cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
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
                    return entity.getPrice() * cartItem.getQuantity();
                })
                .sum();


    }
    @Override
    @KafkaListener(topics = PRODUCT_RESPONSE_TOPIC , groupId = "cart-group")
    public boolean checkInventoryBeforeCheckOut(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(()-> new NotFoundException("Cart not found"));
         for (CartItemEntity item:cart.getCartItems()){
           //  ProductEntity entity = productRepository.findById(item.getProduct().getId()).orElseThrow(()-> new NotFoundException("Product not found"));
             if (entity.getQuantity() < item.getQuantity()){
                 return false;
             }
         }
       return true;

    }

}*/
}