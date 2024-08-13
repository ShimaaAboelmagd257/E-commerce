package com.techie.ecommerce.domain.model;

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
@Entity(name = "Product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String title;
    private double price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id") // name of the foreign key column in the product table
    private CategoryEntity category;
    private List<String> images;

    private int quantity;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

}
