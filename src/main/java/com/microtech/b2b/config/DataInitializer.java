package com.microtech.b2b.config;

import com.microtech.b2b.entity.User;
import com.microtech.b2b.enums.Role;
import com.microtech.b2b.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CommandLineRunner initDatabase(PasswordEncoder passwordEncoder){
        return args->{
            if(!userRepository.existsByUsername("admin")){
                User admin=User.builder()
                        .username("admin")
                        .email("admin@b2b.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("==================================================");
                System.out.println("✅ Compte ADMIN initialisé avec succès !");
                System.out.println("Username : admin");
                System.out.println("Password : admin123");
                System.out.println("==================================================");
            }else {
                System.out.println("ℹ️ Le compte admin existe déjà en base.");
            }
        };
    }
}
