package com.techie.domain.shippo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TransactionDto {

    private ShipmentDto shipment;
    private String servicelevel_token;
    private String carrier_account;
}
