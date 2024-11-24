package com.techie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductRequestEvent {
    private String eventId;
    private Long cartItemId;
    private Integer productId;
}
