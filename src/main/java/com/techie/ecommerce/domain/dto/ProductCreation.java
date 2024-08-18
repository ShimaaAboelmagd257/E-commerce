package com.techie.ecommerce.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreation {
    private Integer id;
    private String title;
    private double price;
    private String description;
    private Integer categoryId;
    private List<String> images;

}
