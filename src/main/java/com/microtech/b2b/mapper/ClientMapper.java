package com.microtech.b2b.mapper;

import com.microtech.b2b.dto.response.ClientResponse;
import com.microtech.b2b.dto.response.CommandeHistoriqueResponse;
import com.microtech.b2b.entity.Client;
import com.microtech.b2b.entity.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface ClientMapper {
    @Mapping(source="user.id",target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    ClientResponse toResponse(Client client);

    List<ClientResponse> toResponseList(List<Client> clients);
    CommandeHistoriqueResponse toHistoriqueResponse(Commande commande);

    List<CommandeHistoriqueResponse> toHistoriqueResponseList(List<Commande> commandes);
}
