package com.alerta_sp.mvc_admin.dto;

import com.alerta_sp.mvc_admin.model.Sensor;

import java.time.LocalDate;

public record SensorView(
        Long id,
        String codigo,
        LocalDate dataInstalacao,
        String status,
        Long idCorrego,
        String nomeCorrego
) {
    public static SensorView fromEntity(Sensor s) {
        return new SensorView(
                s.getId(),
                s.getCodigo(),
                s.getDataInstalacao(),
                s.getStatus(),
                s.getCorrego().getId(),
                s.getCorrego().getNome()
        );
    }
}
