package com.sa.crm.repository;

import com.sa.crm.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Spring Security necesita este método para cargar el usuario
     * durante la autenticación. Recibe el username del token JWT
     * y devuelve el Usuario completo con su rol.
     * SQL: SELECT * FROM usuarios WHERE username = ?
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Usado al registrar un nuevo usuario para verificar
     * que el username no esté tomado.
     */
    boolean existsByUsername(String username);

    /**
     * Verificar email único al registrar.
     */
    boolean existsByEmail(String email);
}