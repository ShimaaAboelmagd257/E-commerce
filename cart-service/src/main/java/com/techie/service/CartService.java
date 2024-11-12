package com.techie.service;


import com.techie.domain.CartEntity;
import com.techie.domain.CartItemEntity;

import java.util.List;
import java.util.Optional;

public interface CartService {

    CartEntity save(CartEntity cartEntity);

    Optional<CartEntity> findById(Long cartId);

    //CartEntity addToCart(Long cartId, ProductDto productDto);

    void delete(Long cartId, Integer productId);

    Optional<CartItemEntity> getCartItem(Long cartId, Integer productId);

    List<CartItemEntity> getCartItems(Long cartId);

    void removeAllCartItems(Long cartId);

    double getCartTotalPrice(Long cartId);

    boolean checkInventoryBeforeCheckOut(Long cartId);

    CartEntity addToCart(Long cartId, Long productId);
}