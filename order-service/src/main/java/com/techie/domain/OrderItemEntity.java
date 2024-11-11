package com.techie.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

  /*  @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;*/

    private int quantity;

    private double price;
   /* public OrderItemEntity(ProductEntity product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }*/

}