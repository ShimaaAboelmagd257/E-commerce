package com.techie.ecommerce.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductFilter {
    private String title;
    private Double price;
    private Double price_min;
    private Double price_max;
    private Long categoryId;
    private Integer limit;
    private Integer offset;


}