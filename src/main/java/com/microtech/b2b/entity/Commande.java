package com.microtech.b2b.entity;

import com.microtech.b2b.enums.CommandeStatut;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    // === Montants (calculés par le service, stockés en base) ===
    @Column(name = "sous_total_ht", nullable = false, precision = 14, scale = 2)
    private BigDecimal sousTotalHT;

    @Column(name = "remise_total", nullable = false, precision = 14, scale = 2)
    @Builder.Default
    private BigDecimal remiseTotal = BigDecimal.ZERO;

    @Column(name = "montant_ht_net", nullable = false, precision = 14, scale = 2)
    private BigDecimal montantHTNet;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal tva;

    @Column(name = "montant_ttc", nullable = false, precision = 14, scale = 2)
    private BigDecimal montantTTC;

    @Column(name = "montant_restant", nullable = false, precision = 14, scale = 2)
    private BigDecimal montantRestant;

    // === Statut & promo ===
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private CommandeStatut statut = CommandeStatut.PENDING;

    @Column(name = "promo_code", length = 20)
    private String promoCode;

    // === Relations ===
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Paiement> paiements = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.dateCreation == null) {
            this.dateCreation = LocalDateTime.now();
        }
        if (this.statut == null) {
            this.statut = CommandeStatut.PENDING;
        }
    }
}