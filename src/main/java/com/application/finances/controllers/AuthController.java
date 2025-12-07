package com.application.finances.controllers;
import com.application.finances.dtos.RegisterUserDTO;
import com.application.finances.entities.User;
import com.application.finances.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterUserDTO data) {
        // Verifica se usuário já existe
        if (this.repository.findByUsername(data.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        // Criptografa a senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // Cria o usuário
        User newUser = new User();
        newUser.setUsername(data.username());
        newUser.setPassword(encryptedPassword);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}