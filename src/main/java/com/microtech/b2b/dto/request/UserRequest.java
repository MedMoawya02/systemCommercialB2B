package com.microtech.b2b.dto.request;

import com.microtech.b2b.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;
    // Optionnel à la mise à jour (null = ne pas changer)
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;
}
