package com.sa.crm.repository;

import com.sa.crm.model.Oportunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OportunidadRepository extends JpaRepository<Oportunidad, Long> {

    /**
     * Lista todas las oportunidades de un cliente.
     * SQL: SELECT * FROM oportunidades WHERE cliente_id = ?
     */
    List<Oportunidad> findByClienteId(Long clienteId);

    /**
     * Lista todas las oportunidades asignadas a un vendedor.
     * SQL: SELECT * FROM oportunidades WHERE usuario_id = ?
     * Útil para que cada vendedor vea solo sus oportunidades.
     */
    List<Oportunidad> findByUsuarioId(Long usuarioId);
}