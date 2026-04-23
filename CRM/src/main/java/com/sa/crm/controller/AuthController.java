package com.sa.crm.controller;


import com.sa.crm.dto.AuthDTO;
import com.sa.crm.dto.AuthResponseDTO;
import com.sa.crm.dto.UsuarioDTO;
import com.sa.crm.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        AuthResponseDTO response = authService.registrarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthDTO authDTO) {
        AuthResponseDTO response = authService.login(authDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestHeader("Authorization") String token) {
        AuthResponseDTO response = authService.refreshToken(token);
        return ResponseEntity.ok(response);
    }
}