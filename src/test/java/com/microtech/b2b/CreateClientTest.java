package com.microtech.b2b;

import com.microtech.b2b.dto.request.ClientCreateRequest;
import com.microtech.b2b.dto.response.ClientResponse;
import com.microtech.b2b.entity.Client;
import com.microtech.b2b.entity.User;
import com.microtech.b2b.mapper.ClientMapper;
import com.microtech.b2b.repository.ClientRepository;
import com.microtech.b2b.repository.CommandeRepository;
import com.microtech.b2b.repository.UserRepository;
import com.microtech.b2b.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateClientTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientServiceImpl clientService;
    @Test
    void shouldCreateClientSuccessfully() {

        // Arrange : préparer les données

        ClientCreateRequest request = new ClientCreateRequest();
        request.setUsername("client1");
        request.setEmail("client@test.com");
        request.setPassword("123456");
        request.setNomEntreprise("Entreprise ABC");

//        when(userRepository.existsByUsername("client1"))
//                .thenReturn(false);

//        when(userRepository.existsByEmail("client@test.com"))
//                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(clientRepository.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ClientResponse expectedResponse = new ClientResponse();

        when(clientMapper.toResponse(any(Client.class)))
                .thenReturn(expectedResponse);


        // Act : exécuter la méthode

        ClientResponse result = clientService.create(request);


        // Assert : vérifier le résultat

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(userRepository).save(any(User.class));
        verify(clientRepository).save(any(Client.class));

        verify(passwordEncoder).encode("123456");
        verify(clientMapper).toResponse(any(Client.class));
    }
}
