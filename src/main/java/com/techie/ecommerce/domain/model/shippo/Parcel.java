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
@Table(name = "parcel")
public class Parcel {
    @Id
    private String id;
    private String length;
    private String width;
    private String  height;
    private String  distance_unit;
    private String weight;
    private String mass_unit;
}
