package com.alerta_sp.mvc_admin.dto;

import com.alerta_sp.mvc_admin.model.Sensor;

import java.time.LocalDate;

/**
 * DTO de saída (view) para representar um Sensor na lista, na tela.
 */
public record SensorView(
        Long id,
        String codigo,
        LocalDate dataInstalacao,
        String status,
        Long idCorrego
) {
    /**
     * Converte uma entidade Sensor em SensorView.
     */
    public static SensorView fromEntity(Sensor sensor) {
        return new SensorView(
                sensor.getId(),
                sensor.getCodigo(),
                sensor.getDataInstalacao(),
                sensor.getStatus(),
                // pega o ID do córrego associado
                sensor.getCorrego().getId()
        );
    }
}
