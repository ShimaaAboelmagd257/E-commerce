package com.techie.ecommerce.domain.model.shippo;

import jakarta.persistence.*;
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
@Table(name = "transaction_response")

public class TransactionsResponse {

        @Id
        private String object_id;  // Matches "object_id" in JSON
        private String object_state;  // Matches "object_state" in JSON
        private String status;
        private LocalDateTime object_created;
        private LocalDateTime object_updated;
        private String object_owner;
        private boolean was_test;
        private String tracking_number;
        private String tracking_url_provider;
        private LocalDateTime eta;
        private String label_url;
        private String commercial_invoice_url;
        private String metadata;

        @OneToOne(cascade = CascadeType.ALL)
        private RateEntity rate;

        @OneToOne(cascade = CascadeType.ALL)
        private TrackingStatusEntity tracking_status;

        @ElementCollection
        private List<String> messages;
}
