package com.techie.ecommerce.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.CategoryEntity;
import com.techie.ecommerce.domain.model.OrderItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Integer id;
    private String title;
    private double price;
    private String description;
    private CategoryDto category;
    private List<String> images;
    private int quantity;
    private List<OrderItemDto> orderItems;
    private CartItemDto cart;

}
