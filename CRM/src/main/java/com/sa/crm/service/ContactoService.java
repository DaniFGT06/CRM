package com.sa.crm.service;

import com.sa.crm.model.Cliente;
import com.sa.crm.model.Contacto;
import com.sa.crm.repository.ContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SRP: ContactoService solo gestiona contactos.
 * Depende de ClienteService para obtener el cliente padre,
 * pero no mezcla lógica de negocio de otras entidades.
 */
@Service
@RequiredArgsConstructor
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final ClienteService clienteService;

    /**
     * Lista todos los contactos de un cliente.
     * Primero verifica que el cliente exista (lanza excepción si no).
     */
    @Transactional(readOnly = true)
    public List<Contacto> listarPorCliente(Long clienteId) {
        clienteService.obtenerClientePorId(clienteId); // valida existencia
        return contactoRepository.findByClienteId(clienteId);
    }

    /**
     * Asocia un nuevo contacto a un cliente existente.
     * El clienteId viene del path del endpoint (ej: /clientes/5/contactos).
     */
    @Transactional
    public Contacto crearContacto(Long clienteId, Contacto contacto) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        contacto.setCliente(cliente);
        return contactoRepository.save(contacto);
    }

    /**
     * Elimina un contacto por su ID.
     */
    @Transactional
    public void eliminarContacto(Long id) {
        Contacto contacto = contactoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado con ID: " + id));
        contactoRepository.delete(contacto);
    }
}