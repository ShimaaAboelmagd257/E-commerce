package com.techie.domain.shippoentity;

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
@Table(name = "transaction")
public class TransactionEntity {

    private ShipmentEntity shipment;
    private String servicelevel_token;
    private String carrier_account;


}
