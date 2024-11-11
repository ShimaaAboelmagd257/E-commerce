package com.techie.domain.shippo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TrackingStatusDTO {
    private String object_id;
    private LocalDateTime object_created;
    private String status;
    private String status_details;
    private LocalDateTime status_date;
}
