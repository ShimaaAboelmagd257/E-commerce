package com.techie.domain.shippoentity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class VerificationsEntity {
    @Embedded
    private boolean isValid;
    @Embedded
    private List<ValidationMessage> messages;

}