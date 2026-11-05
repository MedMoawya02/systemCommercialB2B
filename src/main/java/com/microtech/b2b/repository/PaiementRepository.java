package com.microtech.b2b.repository;

import com.microtech.b2b.entity.Paiement;
import com.microtech.b2b.enums.PaiementStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByCommandeId(Long commandeId);
    List<Paiement> findByStatut(PaiementStatut statut);
}