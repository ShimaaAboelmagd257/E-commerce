package com.techie.ecommerce.domain.dto.shippo;

import com.techie.ecommerce.domain.model.shippo.AddressEntity;
import com.techie.ecommerce.domain.model.shippo.Parcel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ShipmentDto {
   // private Long id;
    private AddressDto address_from;
    private AddressDto address_to;
    private ParcelDto parcels;
    private boolean async;

}
