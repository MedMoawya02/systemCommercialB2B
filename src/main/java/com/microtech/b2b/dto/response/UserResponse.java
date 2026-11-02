package com.microtech.b2b.dto.response;

import com.microtech.b2b.enums.Role;
import lombok.Data;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime localDateTime;
}
