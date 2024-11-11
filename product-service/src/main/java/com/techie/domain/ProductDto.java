package com.techie.domain;

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
    private Long categoryId;
    private List<String> images;
    private int quantity;
  //  private List<OrderItemDto> orderItems;
   // private CartItemDto cart;

}
