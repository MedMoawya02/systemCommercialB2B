package com.microtech.b2b.util;

/**
 * Clés utilisées pour stocker des informations dans la HttpSession.
 * Centralisées pour éviter les fautes de frappe.
 */
public final class SessionKeys {

    private SessionKeys() {
        // Classe utilitaire, pas d'instanciation
    }

    /** Clé pour l'utilisateur connecté (objet User stocké en session). */
    public static final String CURRENT_USER = "CURRENT_USER";

    /** Clé pour le rôle de l'utilisateur connecté (Role). */
    public static final String CURRENT_ROLE = "CURRENT_ROLE";

    /** Clé pour l'identifiant de l'utilisateur connecté (Long). */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /** Clé pour le username de l'utilisateur connecté (String). */
    public static final String CURRENT_USERNAME = "CURRENT_USERNAME";
}