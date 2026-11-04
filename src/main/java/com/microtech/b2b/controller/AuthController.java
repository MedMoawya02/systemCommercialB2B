package com.microtech.b2b.controller;

import com.microtech.b2b.dto.request.LoginRequest;
import com.microtech.b2b.dto.response.AuthResponse;
import com.microtech.b2b.entity.User;
import com.microtech.b2b.enums.Role;
import com.microtech.b2b.exception.UnauthorizedException;
import com.microtech.b2b.service.UserService;
import com.microtech.b2b.util.SessionKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request,HttpServletRequest httpRequest){
        User user=userService.authentificate(request.getUsername(), request.getPassword());
        //derniere session si il existe
        HttpSession oldSession=httpRequest.getSession(false);
        if(oldSession!=null){
            oldSession.invalidate();
        }
        // 3. Créer une nouvelle session (le cookie JSESSIONID sera renvoyé automatiquement)
        HttpSession session= httpRequest.getSession(true);
        session.setAttribute(SessionKeys.CURRENT_USER_ID, user.getId());
        session.setAttribute(SessionKeys.CURRENT_USERNAME, user.getUsername());
        session.setAttribute(SessionKeys.CURRENT_ROLE, user.getRole());
        session.setMaxInactiveInterval(30 * 60); // 30 minutes

        AuthResponse response=AuthResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .message("connexion réussie")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request){
        HttpSession session= request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        AuthResponse response=AuthResponse.builder()
                .message("Deconnexion réussie")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> me(HttpServletRequest request){
        //1.Récupurez la session exsitante sans crée
        HttpSession session= request.getSession(false);
        //2.verification d'un session contient un user
        if(session==null ||session.getAttribute(SessionKeys.CURRENT_USER_ID)==null){
            throw new UnauthorizedException("Aucune session active");
        }
        // 3. Extraire les infos de la session (sans requête BDD, plus rapide)
        Long id=(Long)session.getAttribute(SessionKeys.CURRENT_USER_ID);
        String name=(String)session.getAttribute(SessionKeys.CURRENT_USERNAME);
        Role role=(Role) session.getAttribute(SessionKeys.CURRENT_ROLE);
        //4.construire la réponse
        AuthResponse response= AuthResponse.builder()
                .id(id)
                .username(name)
                .role(role)
                .build();
        return ResponseEntity.ok(response);
    }
}
