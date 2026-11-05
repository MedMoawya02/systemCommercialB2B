package com.microtech.b2b.service.impl;

import com.microtech.b2b.dto.request.ClientCreateRequest;
import com.microtech.b2b.dto.request.ClientUpdateRequest;
import com.microtech.b2b.dto.response.ClientResponse;
import com.microtech.b2b.dto.response.ClientStatsResponse;
import com.microtech.b2b.dto.response.CommandeHistoriqueResponse;
import com.microtech.b2b.entity.Client;
import com.microtech.b2b.entity.User;
import com.microtech.b2b.enums.CommandeStatut;
import com.microtech.b2b.enums.LoyaltyTier;
import com.microtech.b2b.enums.Role;
import com.microtech.b2b.exception.BusinessException;
import com.microtech.b2b.exception.ResourceNotFoundException;
import com.microtech.b2b.mapper.ClientMapper;
import com.microtech.b2b.repository.ClientRepository;
import com.microtech.b2b.repository.CommandeRepository;
import com.microtech.b2b.repository.UserRepository;
import com.microtech.b2b.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final CommandeRepository commandeRepository;
    @Override
    public ClientResponse create(ClientCreateRequest request) {
        if(clientRepository.existsByUserEmail(request.getEmail())){
            throw new BusinessException("Cet email existe deja");
        }
        //2.création user
        User user=User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        //3.création du client liée au user
        Client client=Client.builder()
                .user(user)
                .nomEntreprise(request.getNomEntreprise())
                .loyaltyTier(LoyaltyTier.BASIC)
                .totalOrders(0)
                .totalSpent(BigDecimal.ZERO)
                .build();
        clientRepository.save(client);
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        Client client=clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("client introuvable"));
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findAll() {
        List<Client> clients=clientRepository.findAll();
        return clientMapper.toResponseList(clients);
    }

    @Override
    public ClientResponse update(Long id, ClientUpdateRequest request) {
        Client client=clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Client introuvable"));
        User user=client.getUser();
        if(!user.getEmail().equals(request.getEmail())&&userRepository.existsByUsername(request.getEmail())){
            throw new BusinessException("Cet email existe");
        }
        user.setEmail(request.getEmail());
        if(request.getPassword()!=null||!request.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        clientRepository.save(client);
        client.setNomEntreprise(request.getNomEntreprise());
        clientRepository.save(client);
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public void  delete(Long id) {
        Client client=clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Client introuvable"));
        //ne supprime pas le client si a des commandes
        if(commandeRepository.existsByClientId(client.getId())){
            throw new BusinessException(
                    "Impossible de supprimer : ce client a des commandes existantes");
        }
        Long userId=client.getUser().getId();
        clientRepository.delete(client);
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientStatsResponse getStats(Long clientId) {
        Client client=clientRepository.findById(clientId).orElseThrow(()->new ResourceNotFoundException("Client introuvable"));
        long totalCmds=commandeRepository.countByClientIdAndStatut(clientId, CommandeStatut.CONFIRMED);
        BigDecimal montant=commandeRepository.sumMontantTTCByClientIdAndStatut(clientId,CommandeStatut.CONFIRMED);
        LocalDateTime premiere=commandeRepository.findFirstDateByClientIdAndStatut(clientId,CommandeStatut.CONFIRMED);
        LocalDateTime derniere=commandeRepository.findLastDateByClientIdAndStatut(clientId,CommandeStatut.CONFIRMED);
        ClientStatsResponse response=ClientStatsResponse.builder()
                .clientId(clientId)
                .nomEntreprise(client.getNomEntreprise())
                .totalCommandesConfirmees(totalCmds)
                .montantCumuleDepense(montant!=null?montant:BigDecimal.ZERO)
                .datePremiereCommande(premiere)
                .dateDerniereCommande(derniere)
                .build();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeHistoriqueResponse> getHistorique(Long clientID) {
        if(!clientRepository.existsById(clientID)){
            throw new ResourceNotFoundException("Client introuvable");
        }
        return clientMapper.toHistoriqueResponseList(
                commandeRepository.findByClientIdOrderByDateCreationDesc(clientID)
        );
    }
}
