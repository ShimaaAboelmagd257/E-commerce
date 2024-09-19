package com.techie.ecommerce.testUtl;

import com.techie.ecommerce.domain.dto.*;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.CartItemEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CartDtoUtil {

    public static CartDto createCart() {
        // Create the CartItemDto objects
        CartItemDto item1 = CartItemDto.builder()
                .productId(101L)
                .quantity(2)
                .price(29.99)
                .build();

        CartItemDto item2 = CartItemDto.builder()
                .productId(102L)
                .quantity(1)
                .price(49.99)
                .build();

        // Create the CartDto object
        CartDto cartDto = CartDto.builder()
                .userId(1L)
                .cartItems(Arrays.asList(item1, item2))
                .totalPrice(109.97)
                .createdAt(LocalDateTime.parse("2024-09-09T14:30:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        return cartDto;
    }
    public static CartDto createCartDto() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(1L);
        cartItemDto.setProductId(100L);
        cartItemDto.setQuantity(2);
        cartItemDto.setPrice(50.0);

        CartDto cartDto = new CartDto();
        cartDto.setCartId(null);  // This will be generated after saving
        cartDto.setUserId(1L);
        cartDto.setCartItems(Collections.singletonList(cartItemDto));
        cartDto.setTotalPrice(100.0);
        cartDto.setCreatedAt(LocalDateTime.now());
        return cartDto;
    }
    public static CartEntity createCartEntity() {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(1L);
        cartItemEntity.getProduct().setProductId(100);
        cartItemEntity.setQuantity(2);
        cartItemEntity.setPrice(50.0);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartId(null);  // Simulate unsaved state
        cartEntity.getUser().setId(100L);
        cartEntity.setCartItems(Collections.singletonList(cartItemEntity));
        cartEntity.setTotalPrice(100.0);
        cartEntity.setCreatedAt(LocalDateTime.now());
        return cartEntity;
    }
    public static CartEntity createSavedCartEntity() {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(1L);
        cartItemEntity.getProduct().setProductId(100);
        cartItemEntity.setQuantity(2);
        cartItemEntity.setPrice(50.0);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartId(1L);  // Simulate the saved state with generated cartId
        cartEntity.getUser().setId(100L);
        cartEntity.setCartItems(Collections.singletonList(cartItemEntity));
        cartEntity.setTotalPrice(100.0);
        cartEntity.setCreatedAt(LocalDateTime.now());
        return cartEntity;
    }
}
