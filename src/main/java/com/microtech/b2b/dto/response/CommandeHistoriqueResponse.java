package com.microtech.b2b.dto.response;

import com.microtech.b2b.enums.CommandeStatut;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CommandeHistoriqueResponse {
    private Long id;
    private LocalDateTime dateCreation;
    private BigDecimal montantTTC;
    private CommandeStatut statut;
}