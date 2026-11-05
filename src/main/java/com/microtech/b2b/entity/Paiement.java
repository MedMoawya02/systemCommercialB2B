package com.microtech.b2b.entity;

import com.microtech.b2b.enums.PaiementStatut;
import com.microtech.b2b.enums.TypePaiement;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @Column(name = "numero_paiement", nullable = false, unique = true, length = 50)
    private String numeroPaiement;

    @NotNull
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_paiement", nullable = false, length = 20)
    private TypePaiement typePaiement;

    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement;

    @Column(name = "date_encaissement")
    private LocalDateTime dateEncaissement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private PaiementStatut statut = PaiementStatut.EN_ATTENTE;

    // Référence : n° reçu (espèces), n° chèque (chèque), n° virement (virement)
    @Column(length = 100)
    private String reference;

    // Banque (chèque / virement)
    @Column(length = 100)
    private String banque;

    // Date d'échéance (chèque / virement différé)
    @Column(name = "date_echeance")
    private LocalDate dateEcheance;

    @PrePersist
    protected void onCreate() {
        if (this.datePaiement == null) {
            this.datePaiement = LocalDateTime.now();
        }
        if (this.statut == null) {
            this.statut = PaiementStatut.EN_ATTENTE;
        }
    }
}