package com.microtech.b2b.controller;

import com.microtech.b2b.dto.request.ClientCreateRequest;
import com.microtech.b2b.dto.request.ClientUpdateRequest;
import com.microtech.b2b.dto.response.ClientResponse;
import com.microtech.b2b.dto.response.ClientStatsResponse;
import com.microtech.b2b.dto.response.CommandeHistoriqueResponse;
import com.microtech.b2b.entity.Client;
import com.microtech.b2b.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(request));
    }
    @GetMapping
    public List<ClientResponse> findAll(){
        return clientService.findAll();
    }
    @GetMapping("/{id}")
    public ClientResponse findById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @PutMapping("/{id}")
    public ClientResponse update(Long id, @Valid @PathVariable ClientUpdateRequest request){
        return clientService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/stats")
    public ClientStatsResponse getStats(@PathVariable Long id) {
        return clientService.getStats(id);
    }

    @GetMapping("/{id}/commandes")
    public List<CommandeHistoriqueResponse> getHistorique(@PathVariable Long id) {
        return clientService.getHistorique(id);
    }
}
