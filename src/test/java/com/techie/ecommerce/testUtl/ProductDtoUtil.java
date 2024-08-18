package com.techie.ecommerce.testUtl;

import com.techie.ecommerce.domain.dto.CategoryDto;
import com.techie.ecommerce.domain.dto.ProductCreation;
import com.techie.ecommerce.domain.dto.ProductDto;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.OrderItemEntity;

import java.util.Arrays;
import java.util.Random;

public final class ProductDtoUtil {

    private static final Random RANDOM = new Random();
    public static ProductCreation creation(){
        return ProductCreation.builder()
                .title("Sample Product")
                .price(RANDOM.nextDouble() * 100)
                .description("This is a sample product description.")
                .categoryId(1).images(Arrays.asList(
                        "https://placeimg.com/640/480/any?r=" + RANDOM.nextDouble(),
                        "https://placeimg.com/640/480/any?r=" + RANDOM.nextDouble(),
                        "https://placeimg.com/640/480/any?r=" + RANDOM.nextDouble()
                ))
                .build();
    }
    // Method to generate a sample ProductDto
    public static ProductDto createSampleProductDto() {
        return ProductDto.builder()
                .id(RANDOM.nextInt(1000))
                .title("Sample Product")
                .price(RANDOM.nextDouble() * 100)
                .description("This is a sample product description.")
                //.category(createSampleCategoryDto())
                .images(Arrays.asList(
                        "https://placeimg.com/640/480/any?r=" + RANDOM.nextDouble(),
                        "https://placeimg.com/640/480/any?r=" + RANDOM.nextDouble(),
                        "https://placeimg.com/640/480/any?r=" + RANDOM.nextDouble()
                ))
                .quantity(RANDOM.nextInt(100))
                .orderItems(Arrays.asList(createSampleOrderItemEntity()))
                .cart(createSampleCartEntity())
                .build();
    }
    // Method to generate a sample CategoryDto
    public static CategoryDto createSampleCategoryDto() {
        return CategoryDto.builder()
                .id(1)
                .name("Clothes")
                .image("https://i.imgur.com/QkIa5tT.jpeg")
                .build();
    }

    // Method to generate a sample OrderItemEntity (replace with actual fields)
    public static OrderItemEntity createSampleOrderItemEntity() {
        OrderItemEntity item = new OrderItemEntity();
        return item;
    }

    public static CartEntity createSampleCartEntity() {
        CartEntity cart = new CartEntity();
        return cart;
    }
}

