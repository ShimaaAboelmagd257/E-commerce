package com.techie.ecommerce.domain.dto;

import com.techie.ecommerce.domain.model.ProductEntity;
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

public class CartDto {
    private Long cartId;
    private List<ProductEntity> productEntities;
}
