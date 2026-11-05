package com.microtech.b2b.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produit_id", nullable = false)
    private Product produit;

    @NotNull
    @Min(value = 1, message = "La quantité doit être au moins 1")
    @Column(nullable = false)
    private Integer quantite;

    // Snapshot du prix au moment de la commande (ne change jamais si le produit évolue)
    @Column(name = "prix_unitaire_ht", nullable = false, precision = 14, scale = 2)
    private BigDecimal prixUnitaireHT;

    @Column(name = "total_ligne_ht", nullable = false, precision = 14, scale = 2)
    private BigDecimal totalLigneHT;
}