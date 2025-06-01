package com.alerta_sp.mvc_admin.dto;

import com.alerta_sp.mvc_admin.model.Administrador;

/**
 * Record para exibição de dados de Administrador.
 */
public record AdministradorView(
        Long id,
        String username
) {
    /**
     * Converte uma entidade Administrador em AdministradorView.
     */
    public static AdministradorView fromEntity(Administrador admin) {
        return new AdministradorView(
                admin.getId(),
                admin.getUsername()
        );
    }
}
