package com.sa.crm.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

/**
 * Representa una oportunidad comercial vinculada a un Cliente
 * y asignada a un Usuario (vendedor).
 */
@Entity
@Table(name = "oportunidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oportunidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    /**
     * BigDecimal para valores monetarios.
     * Nunca usar double/float para dinero: tienen errores de precisión
     * en punto flotante (ej: 0.1 + 0.2 ≠ 0.3 en IEEE 754).
     * precision=15, scale=2 → hasta 9,999,999,999,999.99
     */
    @NotNull
    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    /**
     * Estado de la oportunidad: NUEVA, EN_PROCESO, GANADA, PERDIDA.
     * Se podría convertir a Enum en una siguiente iteración.
     */
    @NotBlank
    @Column(nullable = false)
    private String estado;

    /**
     * Relación N:1 con Cliente.
     * Una oportunidad pertenece a un solo cliente.
     * mappedBy en Cliente apunta a este campo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Relación N:1 con Usuario.
     * La oportunidad está asignada al vendedor que la gestiona.
     * No usamos mappedBy en Usuario porque no necesitamos navegar
     * desde Usuario → lista de oportunidades en este modelo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
