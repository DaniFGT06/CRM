package com.sa.crm.model;
/**
 * Enum que representa los roles del sistema.
 * Se almacena como String en la BD (no como ordinal)
 * para que sea legible y no dependa del orden de declaración.
 */
public enum RolUsuario {
    ADMIN,
    VENDEDOR,
    LECTOR
}
