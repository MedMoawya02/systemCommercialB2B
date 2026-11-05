package com.microtech.b2b.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientUpdateRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le nom de l'entreprise est obligatoire")
    @Size(max = 150)
    private String nomEntreprise;

    // Optionnel : si fourni, le mot de passe du User sera réinitialisé
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
}