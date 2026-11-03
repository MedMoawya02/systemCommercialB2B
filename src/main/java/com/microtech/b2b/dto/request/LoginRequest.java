package com.microtech.b2b.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "email est requis")
    private String email;
    @NotBlank(message = "password est requis")
    private String password;
}
