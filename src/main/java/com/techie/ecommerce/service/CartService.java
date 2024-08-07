package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface CartService {

    CartEntity save(CartEntity cartEntity);

    CartEntity addToCart(Long cartId, ProductDto productDto);

    void delete(Long cartId, Long productId);

    Optional<ProductEntity> getCartItem(Long cartId, Long productId);

    List<ProductEntity> getCartItems(Long cartId);

    void removeAllCartItems(Long cartId);

    double getCartTotalPrice(Long cartId);
}
