package com.techie.service;

import com.techie.domain.CartEntity;
import com.techie.domain.CartItemEntity;
import com.techie.domain.ProductResponse;
import com.techie.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private Map<Long , CompletableFuture<ProductResponse>> productFutureMap  = new ConcurrentHashMap<>();

    private static final String PRODUCT_REQUEST_TOPIC = "product-request";
    private static final String PRODUCT_RESPONSE_TOPIC = "product-response";

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
    public CartEntity addToCart(Long cartId, Long productId) {
        // Fetch cart by ID
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CompletableFuture<ProductResponse> productFuture = new CompletableFuture<>();
        productFutureMap.put(productId, productFuture);
        // Send productId to Product Service to request product details
        kafkaTemplate.send(PRODUCT_REQUEST_TOPIC, productId);
        ProductResponse productResponse = productFuture.join();
        CompletableFuture<ProductResponse> removed = productFutureMap.remove(productResponse.getProductId());
        if (removed != null) {
            removed.complete(productResponse);
        }
        // Assume the product details will be processed asynchronously through Kafka Listener
        return cart;  // Kafka Listener will handle the rest of the logic when the response is received
    }

    //    public void requestProductData(Long productId) {
//        kafkaTemplate.send(PRODUCT_REQUEST_TOPIC, productId);
//    }
    // @Override
    @KafkaListener(topics = PRODUCT_RESPONSE_TOPIC, groupId = "cart-group")
    public void handleProductResponse(ProductResponse productResponse, Long cartId) {
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart Not Found"));
        //     ProductEntity product = productRepository.findById(productDto.getId()).orElseThrow(()-> new  ResourceNotFoundException( "Product Not Found"));
        //  kafkaTemplate.send(PRODUCT_REQUEST_TOPIC, productId);

        CartItemEntity existedCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productResponse.getProductId()))
                .findFirst()
                .orElse(null);

        if (existedCartItem != null) {
            existedCartItem.setQuantity(existedCartItem.getQuantity() + 1);
        } else {
            CartItemEntity newCartItem = CartItemEntity.builder()
                    .product(productResponse)
                    .cart(cart)
                    .quantity(1).
                    build();
            cart.getCartItems().add(newCartItem);
        }
        cartRepository.save(cart);
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