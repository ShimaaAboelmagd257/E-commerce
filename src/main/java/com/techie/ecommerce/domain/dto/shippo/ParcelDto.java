package com.techie.ecommerce.domain.dto.shippo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ParcelDto {
    private String length;
    private String width;
    private String  height;
    private String  distance_unit;
    private String weight;
    private String mass_unit;

}
