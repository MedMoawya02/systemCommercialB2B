package com.microtech.b2b.repository;

import com.microtech.b2b.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByUserId(Long userID);
    boolean existsByUserId(Long userId);
    boolean existsByUserEmail(String email);
}
