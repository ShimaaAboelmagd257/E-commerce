package com.techie.ecommerce.domain.model;

import com.sun.istack.NotNull;
import com.techie.ecommerce.domain.dto.CategoryDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private double price;
    @Nullable
    private String description;
    private Integer categoryId;
    @Nullable
    @ElementCollection
    private List<String> images;
}
