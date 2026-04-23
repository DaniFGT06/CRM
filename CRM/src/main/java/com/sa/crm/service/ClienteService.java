package com.sa.crm.service;

import com.sa.crm.model.Cliente;
import com.sa.crm.model.RolUsuario;
import com.sa.crm.model.Usuario;
import com.sa.crm.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SRP aplicado: ClienteService SOLO gestiona la lógica de clientes.
 * No mezcla lógica de oportunidades, contactos ni autenticación.
 *
 * @RequiredArgsConstructor de Lombok genera el constructor con los
 * campos final, que es la forma recomendada de inyectar dependencias
 * (inyección por constructor, no por @Autowired en campo).
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Lista todos los clientes.
     * @Transactional(readOnly=true): abre una transacción de solo lectura,
     * lo que mejora el rendimiento porque Hibernate no hace dirty checking.
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por ID. Lanza excepción si no existe.
     * RuntimeException aquí será manejada por @ControllerAdvice en B4.
     */
    @Transactional(readOnly = true)
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    /**
     * Crea un nuevo cliente.
     * Valida que el email no esté registrado antes de guardar.
     */
    @Transactional
    public Cliente crearCliente(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con el email: " + cliente.getEmail());
        }
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza los datos de un cliente existente.
     */
    @Transactional
    public Cliente actualizarCliente(Long id, Cliente datosNuevos) {
        Cliente cliente = obtenerClientePorId(id);

        // Solo actualizamos si el email cambió y el nuevo no está en uso
        if (!cliente.getEmail().equals(datosNuevos.getEmail())
                && clienteRepository.existsByEmail(datosNuevos.getEmail())) {
            throw new RuntimeException("El email ya está en uso: " + datosNuevos.getEmail());
        }

        cliente.setNombre(datosNuevos.getNombre());
        cliente.setEmail(datosNuevos.getEmail());
        cliente.setTelefono(datosNuevos.getTelefono());
        cliente.setEmpresa(datosNuevos.getEmpresa());

        return clienteRepository.save(cliente);
    }

    /**
     * Elimina un cliente. SOLO puede hacerlo un ADMIN.
     *
     * SRP en la capa de servicio: la restricción de rol se verifica
     * aquí, no en el controlador. El controlador solo orquesta,
     * el servicio decide si la operación es válida.
     *
     * Recibe el usuario autenticado para verificar su rol.
     */
    @Transactional
    public void eliminarCliente(Long id, Usuario usuarioAutenticado) {
        if (usuarioAutenticado.getRol() != RolUsuario.ADMIN) {
            throw new AccessDeniedException("Solo los administradores pueden eliminar clientes.");
        }
        Cliente cliente = obtenerClientePorId(id);
        clienteRepository.delete(cliente);
    }
}