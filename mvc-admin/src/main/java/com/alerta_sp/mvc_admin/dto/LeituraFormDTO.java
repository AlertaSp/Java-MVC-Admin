package com.alerta_sp.mvc_admin.dto;

import jakarta.validation.constraints.NotNull;

public class LeituraFormDTO {
    @NotNull
    private Long idSensor;

    @NotNull
    private Double nivel;

    // Getters e Setters

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Double getNivel() {
        return nivel;
    }

    public void setNivel(Double nivel) {
        this.nivel = nivel;
    }
}
