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

public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;


}