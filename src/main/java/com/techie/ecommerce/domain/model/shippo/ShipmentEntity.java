package com.techie.ecommerce.domain.model.shippo;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shipment")

public class ShipmentEntity {
    @Id
    private Long shipmentId;
    private AddressEntity address_from;
    private AddressEntity address_to;
    private Parcel parcels;
    private boolean async;
}
