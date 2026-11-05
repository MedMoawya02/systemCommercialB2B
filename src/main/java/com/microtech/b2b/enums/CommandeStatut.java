package com.microtech.b2b.enums;

public enum CommandeStatut {
    PENDING,     // En attente du règlement intégral
    CONFIRMED,   // Validée par ADMIN (stocks décrémentés)
    CANCELED,    // Annulée par ADMIN
    REJECTED     // Rejetée automatiquement (stock insuffisant)
}