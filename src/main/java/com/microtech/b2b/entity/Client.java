package com.microtech.b2b.entity;

import com.microtech.b2b.enums.LoyaltyTier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 1 Client ↔ 1 User (le user porte l'authentification, le client porte les infos métier)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    private User user;

    @NotBlank(message = "Le nom de l'entreprise est obligatoire")
    @Column(name = "nom_entreprise", nullable = false, length = 150)
    private String nomEntreprise;

    @Enumerated(EnumType.STRING)
    @Column(name = "loyalty_tier",nullable = false,length = 20)
    @Builder.Default
    private LoyaltyTier loyaltyTier = LoyaltyTier.BASIC;

    @Column(name = "total_orders", nullable = false)
    @Builder.Default
    private Integer totalOrders = 0;

    @Column(name = "total_spent", nullable = false, precision = 14, scale = 2)
    @Builder.Default
    private BigDecimal totalSpent = BigDecimal.ZERO;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Commande> commandes=new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.dateCreation == null) {
            this.dateCreation = LocalDateTime.now();
        }
        if (this.loyaltyTier == null) {
            this.loyaltyTier = LoyaltyTier.BASIC;
        }
        if (this.totalOrders == null) {
            this.totalOrders = 0;
        }
        if (this.totalSpent == null) {
            this.totalSpent = BigDecimal.ZERO;
        }
    }
}
