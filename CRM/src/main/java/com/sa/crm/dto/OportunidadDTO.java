package com.sa.crm.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OportunidadDTO {

    private Long id;

    @NotBlank(message = "El nombre de la oportunidad no puede estar vacío")
    private String nombre;

    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado; // PROSPECTO, NEGOCIACION, PROPUESTA, CIERRE

    @NotNull(message = "El cliente es requerido")
    private Long clienteId;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    private String fechaCierre;

    private Integer probabilidad; // 0-100
}