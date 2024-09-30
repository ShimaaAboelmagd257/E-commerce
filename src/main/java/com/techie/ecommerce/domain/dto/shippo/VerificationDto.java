package com.techie.ecommerce.domain.dto.shippo;

import com.techie.ecommerce.domain.model.shippo.ValidationMessage;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationDto {
//    private boolean isValid;
//    private List<ValidationMessage> messages;
}
