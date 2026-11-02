package com.microtech.b2b.repository;

import com.microtech.b2b.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndIdNot(String username,Long id);
    boolean existsByEmailAndIdNot(String email,Long id);
}
