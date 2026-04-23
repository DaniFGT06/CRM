package com.sa.crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad principal del CRM.
 * Un Cliente puede tener múltiples Contactos y múltiples Oportunidades.
 */
@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    private String telefono;

    private String empresa;

    /**
     * Relación 1:N con Contacto.
     *
     * - mappedBy = "cliente": le indica a JPA que la FK vive en la tabla
     *   de Contacto (en el campo 'cliente'), no aquí. Sin esto, JPA
     *   crearía una tabla intermedia innecesaria.
     *
     * - cascade = ALL: cualquier operación sobre Cliente (save, delete)
     *   se propaga a sus contactos. Útil para no borrar contactos huérfanos.
     *
     * - orphanRemoval = true: si quitamos un Contacto de esta lista,
     *   JPA lo elimina automáticamente de la BD.
     *
     * - FetchType.LAZY (por defecto en OneToMany): los contactos NO se
     *   cargan al consultar el cliente, solo cuando accedes a la lista.
     *   Evita N+1 queries y carga innecesaria de datos.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    // @Builder.Default es necesario con Lombok para que el builder
    // inicialice la lista en lugar de dejarla null.
    private List<Contacto> contactos = new ArrayList<>();

    /**
     * Relación 1:N con Oportunidad.
     * Misma lógica que con Contacto.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Oportunidad> oportunidades = new ArrayList<>();
}
