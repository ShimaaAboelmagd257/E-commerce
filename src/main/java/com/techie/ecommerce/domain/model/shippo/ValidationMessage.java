package com.techie.ecommerce.domain.model.shippo;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ValidationMessage {
    private String source;
    private String code;
    private String type;
    private String text;
}
