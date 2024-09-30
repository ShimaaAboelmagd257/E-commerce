package com.techie.ecommerce.domain.model.shippo;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable

public  class RateEntity {
    @Id
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