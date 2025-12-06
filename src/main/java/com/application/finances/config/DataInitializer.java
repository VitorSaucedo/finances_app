package com.application.finances.config;

import com.application.finances.entities.User;
import com.application.finances.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    // Lê a variável de ambiente. Se não existir, usa null.
    @Value("${ADMIN_ROOT_PASSWORD:#{null}}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initUser(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            // Só roda se não houver usuários NO BANCO
            if (repo.count() == 0) {

                if (adminPassword == null || adminPassword.isBlank()) {
                    System.out.println("⚠️ AVISO: Variável ADMIN_ROOT_PASSWORD não configurada. Nenhum usuário criado.");
                    return;
                }

                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode(adminPassword)); // Usa a senha da variável
                repo.save(user);

                System.out.println("✅ SUCESSO: Usuário 'admin' criado com a senha definida na variável de ambiente.");
            } else {
                System.out.println("ℹ️ INFO: Usuários já existem no banco. Nenhuma ação tomada.");
            }
        };
    }
}