package com.microtech.b2b.security;

import com.microtech.b2b.enums.Role;
import com.microtech.b2b.exception.ForbiddenException;
import com.microtech.b2b.util.SessionKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        HttpSession session = request.getSession(false);
        // À ce stade, AuthInterceptor a déjà validé la session. Double-check de sécurité :
        if (session == null) {
            throw new ForbiddenException("Session introuvable");
        }

        Role role = (Role) session.getAttribute(SessionKeys.CURRENT_ROLE);
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // === Matrice d'habilitation du CDC ===

        // 1. Gestion des utilisateurs → ADMIN uniquement
        if (uri.startsWith("/api/users")) {
            if (role != Role.ADMIN) {
                throw new ForbiddenException("Accès réservé aux administrateurs");
            }
        }

        // 2. Gestion des clients → ADMIN uniquement (le client consulte son profil via /api/me/**)
        else if (uri.startsWith("/api/clients")) {
            if (role != Role.ADMIN) {
                throw new ForbiddenException("Accès réservé aux administrateurs");
            }
        }

        // 3. Produits → lecture pour tous, écriture réservée à l'ADMIN
        else if (uri.startsWith("/api/products")) {
            boolean isRead = "GET".equalsIgnoreCase(method);
            if (!isRead && role != Role.ADMIN) {
                throw new ForbiddenException("Modification de produits réservée aux administrateurs");
            }
        }

        // 4. Commandes → ADMIN uniquement (le client consulte son historique via /api/me/**)
        else if (uri.startsWith("/api/commandes")) {
            if (role != Role.ADMIN) {
                throw new ForbiddenException("Accès réservé aux administrateurs");
            }
        }

        // 5. Paiements → ADMIN uniquement
        else if (uri.startsWith("/api/paiements") || uri.matches("/api/commandes/\\d+/paiements.*")) {
            if (role != Role.ADMIN) {
                throw new ForbiddenException("Gestion des paiements réservée aux administrateurs");
            }
        }

        // 6. Espace client (/api/me/**) → réservé aux CLIENTs
        else if (uri.startsWith("/api/me")) {
            if (role != Role.CLIENT) {
                throw new ForbiddenException("Espace réservé aux clients");
            }
        }

        // 7. Routes authentifiées mais sans restriction de rôle (logout, me)
        //    → /api/auth/** déjà géré par WebConfig (login public, logout/me authentifiés)
        //    → Aucune restriction spécifique ici.

        return true;
    }
}