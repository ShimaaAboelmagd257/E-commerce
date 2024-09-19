package com.techie.ecommerce.testUtl;

import com.techie.ecommerce.domain.model.*;

import java.util.Arrays;
import java.util.List;

public final class ProductEntityUtil {


    public static ProductEntity productEntity() {
        return ProductEntity.builder()
                    .cart(List.of(CartItemEntity.builder().id(1L).build())) // Example CartEntity
                    .price(29.99)
                    .title("Sample Product")
                    .description("This is a sample product description.")
                    .images(Arrays.asList("image1.jpg", "image2.jpg"))
                 //   .category(CategoryEntity.builder().name("Electronics").build()) // Example CategoryEntity
                    .orderItems(Arrays.asList(
                            OrderItemEntity.builder().product(null).quantity(2).build(),
                            OrderItemEntity.builder().product(null).quantity(1).build()
                    ))
                    .quantity(10)
                    .build();
    }
}

