package com.techie.ecommerce.domain.dto.shippo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddressDto {
    private String object_id;
    private LocalDateTime object_created;
    private LocalDateTime object_updated;

    private String name;
    private String company;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String email;

    private String metadata;
    private String object_owner;
}
