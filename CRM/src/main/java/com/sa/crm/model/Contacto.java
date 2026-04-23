package com.sa.crm.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Persona de contacto asociada a un Cliente.
 * Aquí vive la FK hacia la tabla de clientes.
 */
@Entity
@Table(name = "contactos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    private String cargo;

    /**
     * Relación N:1 con Cliente.
     *
     * - @ManyToOne: muchos Contactos pertenecen a un solo Cliente.
     *
     * - FetchType.LAZY: NO carga el Cliente completo al traer un Contacto.
     *   Esto es importante para evitar queries en cascada cuando solo
     *   necesitas los datos del contacto.
     *
     * - @JoinColumn(name = "cliente_id"): define el nombre de la FK en la
     *   tabla 'contactos'. Sin esto, JPA elige un nombre automático que
     *   puede variar entre versiones.
     *
     * - nullable = false: un contacto SIEMPRE debe pertenecer a un cliente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
