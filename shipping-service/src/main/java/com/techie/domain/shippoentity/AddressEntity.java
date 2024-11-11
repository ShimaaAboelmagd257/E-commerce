package com.techie.domain.shippoentity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "addresses")
public class AddressEntity {

    @Id
    private String object_id;


    @Column(name = "object_created")
    private LocalDateTime object_created;

    @Column(name = "object_updated")
    private LocalDateTime object_updated;

    private String name;
    private String company;
    private String street_no;
    @Column(name = "street_1")
    private String street1;

    @Column(name = "street_2")
    private String street2;

    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String email;
    private String metadata;
    private Boolean is_complete;

    @Column(name = "object_owner")
    private String object_owner;
    @Embedded
    private VerificationsEntity validation_results;
}
