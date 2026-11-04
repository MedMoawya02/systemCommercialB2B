package com.microtech.b2b;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HashCheckTest {

    @Test
    void checkAdminPasswordHash() {
        // ⬇️ Collez ici le hash récupéré en base
        String hashFromDatabase = "$2a$10$mc7/l/oeUC2VixgSZaunMuZVAycEUZPdGc7Zx.engYgL2FI8x9Mdi";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        boolean matches = encoder.matches("admin123", hashFromDatabase);

        System.out.println("=========================================");
        System.out.println("Hash en BDD       : " + hashFromDatabase);
        System.out.println("Mot de passe test : admin123");
        System.out.println("Résultat matches  : " + matches);
        System.out.println("=========================================");

        assertTrue(matches, "Le hash en base ne correspond pas à 'admin123'");
    }
}