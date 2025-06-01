package com.alerta_sp.mvc_admin.dto;

import com.alerta_sp.mvc_admin.model.Corrego;

public record CorregoView(
        Long id,
        String nome,
        String latitude,
        String longitude,
        Double nivelAlerta,
        Double nivelCritico
) {
    /**
     * Converte uma entidade Corrego em CorregoView.
     */
    public static CorregoView fromEntity(Corrego corrego) {
        return new CorregoView(
                corrego.getId(),
                corrego.getNome(),
                corrego.getLatitude(),
                corrego.getLongitude(),
                corrego.getNivelAlerta(),
                corrego.getNivelCritico()
        );
    }
}
