package com.sa.crm.service;

import com.sa.crm.model.Usuario;
import com.sa.crm.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UsuarioService implementa UserDetailsService de Spring Security.
 *
 * Esto permite que Spring Security use este servicio para cargar
 * el usuario desde la BD cuando llega una petición con JWT.
 * El método loadUserByUsername es invocado automáticamente por el
 * filtro de autenticación que crearemos en B5.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Requerido por UserDetailsService.
     * Spring Security llama este método al validar el token JWT.
     * Devuelve un UserDetails que usamos para obtener roles y permisos.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username));

        // Convertimos nuestro Usuario a un UserDetails de Spring Security.
        // SimpleGrantedAuthority requiere el prefijo "ROLE_" para que
        // hasRole("ADMIN") funcione correctamente en las anotaciones de seguridad.
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities("ROLE_" + usuario.getRol().name())
                .build();
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * La contraseña se hashea con BCrypt antes de guardarla.
     * Nunca se guarda la contraseña en texto plano.
     */
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El username ya está en uso: " + usuario.getUsername());
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está en uso: " + usuario.getEmail());
        }
        // Hashear la contraseña antes de persistir
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene el objeto Usuario completo (con rol) a partir del username.
     * Usado en los controladores para pasar el usuario autenticado
     * a los servicios que necesitan verificar permisos.
     */
    @Transactional(readOnly = true)
    public Usuario obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
    }
}