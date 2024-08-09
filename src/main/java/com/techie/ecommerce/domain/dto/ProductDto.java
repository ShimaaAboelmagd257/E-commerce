package com.techie.ecommerce.domain.dto;

import com.techie.ecommerce.domain.model.CartEntity;
import com.techie.ecommerce.domain.model.CategoryEntity;
import com.techie.ecommerce.domain.model.OrderItemEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private Long productId;
    private String title;
    private double price;
    private String description;
    private String category;
    private List<String> images;
    private int quantity;
    private List<OrderItemEntity> orderItems;
    private CartEntity cart;
}
