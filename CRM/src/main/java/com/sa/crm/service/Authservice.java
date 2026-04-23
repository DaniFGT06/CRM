package com.sa.crm.service;


import com.sa.crm.dto.AuthDTO;
import com.sa.crm.dto.AuthResponseDTO;
import com.sa.crm.dto.UsuarioDTO;
import com.sa.crm.exception.ResourceNotFoundException;
import com.sa.crm.exception.UserAlreadyExistsException;
import com.sa.crm.model.Usuario;
import com.sa.crm.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioDetailsService usuarioDetailsService;

    public AuthResponseDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new UserAlreadyExistsException("El usuario con email " + usuarioDTO.getEmail() + " ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setRol(usuarioDTO.getRol());

        usuarioRepository.save(usuario);

        UserDetails userDetails = usuarioDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtService.generateToken(userDetails, usuario.getRol());

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol());
        response.setMensaje("Usuario registrado exitosamente");

        return response;
    }

    public AuthResponseDTO login(AuthDTO authDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDTO.getEmail(),
                            authDTO.getPassword()
                    )
            );

            Usuario usuario = usuarioRepository.findByEmail(authDTO.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(usuario.getEmail());
            String token = jwtService.generateToken(userDetails, usuario.getRol());

            AuthResponseDTO response = new AuthResponseDTO();
            response.setToken(token);
            response.setEmail(usuario.getEmail());
            response.setRol(usuario.getRol());
            response.setMensaje("Login exitoso");

            return response;
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Email o contraseña incorrectos");
        }
    }

    public AuthResponseDTO refreshToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtService.isTokenValid(token)) {
            throw new ResourceNotFoundException("Token inválido o expirado");
        }

        String userEmail = jwtService.extractUserEmail(token);
        String rol = jwtService.extractRole(token);

        UserDetails userDetails = usuarioDetailsService.loadUserByUsername(userEmail);
        String newToken = jwtService.generateToken(userDetails, rol);

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(newToken);
        response.setEmail(userEmail);
        response.setRol(rol);
        response.setMensaje("Token renovado exitosamente");

        return response;
    }
}