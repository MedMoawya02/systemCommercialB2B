package com.microtech.b2b.dto.response;

import com.microtech.b2b.enums.LoyaltyTier;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ClientResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String nomEntreprise;
    private LoyaltyTier loyaltyTier;
    private Integer totalOrders;
    private BigDecimal totalSpent;
    private LocalDateTime dateCreation;
}