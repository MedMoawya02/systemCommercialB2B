package com.microtech.b2b.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientStatsResponse {
    private Long clientId;
    private String nomEntreprise;
    private long totalCommandesConfirmees;
    private BigDecimal montantCumuleDepense;
    private LocalDateTime datePremiereCommande;
    private LocalDateTime dateDerniereCommande;
}