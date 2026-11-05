package com.microtech.b2b.repository;

import com.microtech.b2b.entity.Commande;
import com.microtech.b2b.enums.CommandeStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByClientIdOrderByDateCreationDesc(Long clientId);

    List<Commande> findByStatut(CommandeStatut statut);

    // === Agrégats pour les stats clients ===
    long countByClientIdAndStatut(Long clientId, CommandeStatut statut);

    @Query("SELECT COALESCE(SUM(c.montantTTC), 0) FROM Commande c " +
            "WHERE c.client.id = :clientId AND c.statut = :statut")
    BigDecimal sumMontantTTCByClientIdAndStatut(
            @Param("clientId") Long clientId,
            @Param("statut") CommandeStatut statut);

    @Query("SELECT MIN(c.dateCreation) FROM Commande c " +
            "WHERE c.client.id = :clientId AND c.statut = :statut")
    LocalDateTime findFirstDateByClientIdAndStatut(
            @Param("clientId") Long clientId,
            @Param("statut") CommandeStatut statut);

    @Query("SELECT MAX(c.dateCreation) FROM Commande c " +
            "WHERE c.client.id = :clientId AND c.statut = :statut")
    LocalDateTime findLastDateByClientIdAndStatut(
            @Param("clientId") Long clientId,
            @Param("statut") CommandeStatut statut);

    // Vérifie si le client a au moins une commande (pour le delete)
    boolean existsByClientId(Long clientId);
}