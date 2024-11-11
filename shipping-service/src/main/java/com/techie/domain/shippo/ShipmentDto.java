package com.techie.domain.shippo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentDto {
    private Long id;
    private AddressDto address_from;
    private AddressDto address_to;
    private ParcelDto parcels;
    private boolean async;

}
