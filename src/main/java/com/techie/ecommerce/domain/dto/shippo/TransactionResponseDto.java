package com.techie.ecommerce.domain.dto.shippo;

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

public class TransactionResponseDto {
    private String object_state;
    private String status;
    private LocalDateTime object_created;
    private LocalDateTime object_updated;
    private String object_id;
    private String object_owner;
    private boolean was_test;
    private RateDto rate;
    private String tracking_number;
    private TrackingStatusDTO tracking_status;
    private String tracking_url_provider;
    private LocalDateTime eta;
    private String label_url;
    private String commercial_invoice_url;
    private String metadata;
    private List<String> messages;
}
