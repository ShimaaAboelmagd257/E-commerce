package com.techie.domain.shippo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RateDto {
    private String object_id;
    private String amount;
    private String currency;
    private String amount_local;
    private String currency_local;
    private String provider;
    private String servicelevel_name;
    private String servicelevel_token;
    private String carrier_account;
}
