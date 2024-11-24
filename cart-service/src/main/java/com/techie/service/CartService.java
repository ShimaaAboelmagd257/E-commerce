package com.techie.service;


import com.techie.domain.CartEntity;
import com.techie.domain.CartItemEntity;
import com.techie.domain.ProductEntity;
import com.techie.domain.ProductResponse;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CartService {

    CartEntity save(CartEntity cartEntity);

    Optional<CartEntity> findById(Long cartId);

    void addToCart(Long cartId , Integer productId);

    void delete(Long cartId, Integer productId);

    Optional<CartItemEntity> getCartItem(Long cartId, Integer productId);

    List<CartItemEntity> getCartItems(Long cartId);

    void removeAllCartItems(Long cartId);

    double getCartTotalPrice(Long cartId);

    //boolean checkInventoryBeforeCheckOut(Long cartId);

    //CompletableFuture<CartEntity> requestProductInfo(Long cartId, Integer productId);

    //void handleProductResponse(ProductEntity productEntity, Long cartId);
}
