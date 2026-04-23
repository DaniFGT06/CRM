package com.sa.crm.repository;

import com.sa.crm.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

    /**
     * Trae todos los contactos de un cliente específico.
     * SQL generado: SELECT * FROM contactos WHERE cliente_id = ?
     *
     * Usamos el ID del cliente y no el objeto completo para evitar
     * cargar la entidad Cliente solo para hacer la consulta.
     */
    List<Contacto> findByClienteId(Long clienteId);
}