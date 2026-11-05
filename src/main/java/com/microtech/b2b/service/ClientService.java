package com.microtech.b2b.service;

import com.microtech.b2b.dto.request.ClientCreateRequest;
import com.microtech.b2b.dto.request.ClientUpdateRequest;
import com.microtech.b2b.dto.response.ClientResponse;
import com.microtech.b2b.dto.response.ClientStatsResponse;
import com.microtech.b2b.dto.response.CommandeHistoriqueResponse;

import java.util.List;

public interface ClientService {
    ClientResponse create(ClientCreateRequest request);
    ClientResponse findById(Long id);
    List<ClientResponse> findAll();
    ClientResponse update(Long id,ClientUpdateRequest request);
    void delete(Long id);

    ClientStatsResponse getStats(Long clientId);
    List<CommandeHistoriqueResponse> getHistorique(Long clientID);
}
