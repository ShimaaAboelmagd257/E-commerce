package com.techie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductResponse {
    private Integer id;
    private String title;
    private double price;
    private String description;
    private List<String> images;
    private LocalDateTime creationAt;
    private LocalDateTime updatedAt;
    private CategoryDto category;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CategoryDto {
        private Integer id;
        private String name;
        private String image;
        private LocalDateTime creationAt;
        private LocalDateTime updatedAt;
    }
}