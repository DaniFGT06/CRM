package com.sa.crm.service;

import com.sa.crm.model.Cliente;
import com.sa.crm.model.Oportunidad;
import com.sa.crm.model.Usuario;
import com.sa.crm.repository.OportunidadRepository;
import com.sa.crm.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SRP: OportunidadService solo gestiona oportunidades comerciales.
 */
@Service
@RequiredArgsConstructor
public class OportunidadService {

    private final OportunidadRepository oportunidadRepository;
    private final ClienteService clienteService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Lista todas las oportunidades de un cliente.
     */
    @Transactional(readOnly = true)
    public List<Oportunidad> listarPorCliente(Long clienteId) {
        clienteService.obtenerClientePorId(clienteId);
        return oportunidadRepository.findByClienteId(clienteId);
    }

    /**
     * Crea una oportunidad asociada a un cliente y asignada
     * al usuario autenticado (el vendedor que la registra).
     *
     * El usuario autenticado se obtiene desde el contexto de seguridad
     * en el controlador y se pasa al servicio.
     * Esto mantiene el SRP: el servicio no accede al contexto de seguridad,
     * solo recibe los datos que necesita.
     */
    @Transactional
    public Oportunidad crearOportunidad(Long clienteId, Oportunidad oportunidad, Usuario usuarioAutenticado) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        oportunidad.setCliente(cliente);
        oportunidad.setUsuario(usuarioAutenticado);
        return oportunidadRepository.save(oportunidad);
    }

    /**
     * Obtiene una oportunidad por ID.
     */
    @Transactional(readOnly = true)
    public Oportunidad obtenerPorId(Long id) {
        return oportunidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oportunidad no encontrada con ID: " + id));
    }

    /**
     * Actualiza el estado o valor de una oportunidad.
     */
    @Transactional
    public Oportunidad actualizarOportunidad(Long id, Oportunidad datosNuevos) {
        Oportunidad oportunidad = obtenerPorId(id);
        oportunidad.setNombre(datosNuevos.getNombre());
        oportunidad.setValor(datosNuevos.getValor());
        oportunidad.setEstado(datosNuevos.getEstado());
        return oportunidadRepository.save(oportunidad);
    }
}