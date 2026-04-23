package com.sa.crm.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Representa a los usuarios del sistema (vendedores, admins, lectores).
 * Implementa UserDetails de Spring Security en la capa de seguridad (ver UsuarioPrincipal).
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY delega la generación de ID a la BD (autoincrement).
    // Se prefiere sobre SEQUENCE en H2 por simplicidad.
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    // unique = true garantiza que no haya dos usuarios con el mismo username.
    private String username;

    @NotBlank
    @Column(nullable = false)
    // La contraseña se guardará hasheada (BCrypt) desde el servicio.
    private String password;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    // EnumType.STRING guarda "ADMIN", "VENDEDOR" o "LECTOR" en la BD.
    // Si usáramos EnumType.ORDINAL (por defecto) guardaría 0, 1, 2 y
    // cambiar el orden del enum rompería todos los registros existentes.
    @Column(nullable = false)
    private RolUsuario rol;
}
