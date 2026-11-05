package com.microtech.b2b.security;

import com.microtech.b2b.exception.UnauthorizedException;
import com.microtech.b2b.util.SessionKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // 1. Récupérer la session SANS la créer
        HttpSession session = request.getSession(false);

        // 2. Vérifier qu'une session existe et contient un utilisateur
        if (session == null || session.getAttribute(SessionKeys.CURRENT_USER_ID) == null) {
            throw new UnauthorizedException("Authentification requise");
        }

        // 3. Tout est OK → laisser passer la requête
        return true;
    }
}