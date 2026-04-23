package com.sa.crm.repository;

import com.sa.crm.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JpaRepository<Cliente, Long> nos da gratis sin escribir nada:
 *   - save(cliente)
 *   - findById(id)
 *   - findAll()
 *   - deleteById(id)
 *   - count(), existsById(), etc.
 *
 * Solo declaramos métodos extra que necesitemos.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Spring genera el SQL automáticamente a partir del nombre del método:
     * SELECT * FROM clientes WHERE email = ?
     * Útil para verificar si ya existe un cliente con ese email.
     */
    Optional<Cliente> findByEmail(String email);

    /**
     * SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
     * FROM clientes WHERE email = ?
     * Usado en el servicio para validar duplicados antes de guardar.
     */
    boolean existsByEmail(String email);
}