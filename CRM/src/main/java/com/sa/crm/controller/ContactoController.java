package com.sa.crm.controller;


import com.sa.crm.dto.ContactoDTO;
import com.sa.crm.service.ContactoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@RequiredArgsConstructor
public class ContactoController {

    private final ContactoService contactoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR', 'LECTOR')")
    public ResponseEntity<List<ContactoDTO>> listarContactos() {
        return ResponseEntity.ok(contactoService.listarContactos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR', 'LECTOR')")
    public ResponseEntity<ContactoDTO> obtenerContacto(@PathVariable Long id) {
        return ResponseEntity.ok(contactoService.obtenerContacto(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ContactoDTO> crearContacto(@Valid @RequestBody ContactoDTO contactoDTO) {
        ContactoDTO nuevoContacto = contactoService.crearContacto(contactoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoContacto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ContactoDTO> actualizarContacto(
            @PathVariable Long id,
            @Valid @RequestBody ContactoDTO contactoDTO) {
        return ResponseEntity.ok(contactoService.actualizarContacto(id, contactoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarContacto(@PathVariable Long id) {
        contactoService.eliminarContacto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR', 'LECTOR')")
    public ResponseEntity<List<ContactoDTO>> obtenerContactosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contactoService.obtenerContactosPorCliente(clienteId));
    }

    @DeleteMapping("/cliente/{clienteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarContactosPorCliente(@PathVariable Long clienteId) {
        contactoService.eliminarContactosPorCliente(clienteId);
        return ResponseEntity.noContent().build();
    }
}