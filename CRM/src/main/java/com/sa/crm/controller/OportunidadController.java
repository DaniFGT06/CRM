package com.sa.crm.controller;


import com.sa.crm.dto.OportunidadDTO;
import com.sa.crm.service.OportunidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oportunidades")
@RequiredArgsConstructor
public class OportunidadController {

    private final OportunidadService oportunidadService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR', 'LECTOR')")
    public ResponseEntity<List<OportunidadDTO>> listarOportunidades() {
        return ResponseEntity.ok(oportunidadService.listarOportunidades());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR', 'LECTOR')")
    public ResponseEntity<OportunidadDTO> obtenerOportunidad(@PathVariable Long id) {
        return ResponseEntity.ok(oportunidadService.obtenerOportunidad(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<OportunidadDTO> crearOportunidad(@Valid @RequestBody OportunidadDTO oportunidadDTO) {
        OportunidadDTO nuevaOportunidad = oportunidadService.crearOportunidad(oportunidadDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOportunidad);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<OportunidadDTO> actualizarOportunidad(
            @PathVariable Long id,
            @Valid @RequestBody OportunidadDTO oportunidadDTO) {
        return ResponseEntity.ok(oportunidadService.actualizarOportunidad(id, oportunidadDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarOportunidad(@PathVariable Long id) {
        oportunidadService.eliminarOportunidad(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR', 'LECTOR')")
    public ResponseEntity<List<OportunidadDTO>> obtenerOportunidadesPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(oportunidadService.obtenerOportunidadesPorCliente(clienteId));
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR', 'LECTOR')")
    public ResponseEntity<List<OportunidadDTO>> obtenerOportunidadesPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(oportunidadService.obtenerOportunidadesPorEstado(estado));
    }

    @DeleteMapping("/cliente/{clienteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarOportunidadesPorCliente(@PathVariable Long clienteId) {
        oportunidadService.eliminarOportunidadesPorCliente(clienteId);
        return ResponseEntity.noContent().build();
    }
}