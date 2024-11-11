package com.techie.domain.shippoentity;

import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class TrackingStatusEntity {
    @Id
    private String object_id;
    private LocalDateTime object_created;
    private String status;
    private String status_details;
    private LocalDateTime status_date;
}
