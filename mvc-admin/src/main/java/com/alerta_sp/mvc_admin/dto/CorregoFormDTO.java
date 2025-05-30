package com.alerta_sp.mvc_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CorregoFormDTO {
    @NotBlank
    private String nome;

    private String latitude;
    private String longitude;

    @NotNull
    private Double nivelAlerta;

    @NotNull
    private Double nivelCritico;

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getNivelAlerta() {
        return nivelAlerta;
    }

    public void setNivelAlerta(Double nivelAlerta) {
        this.nivelAlerta = nivelAlerta;
    }

    public Double getNivelCritico() {
        return nivelCritico;
    }

    public void setNivelCritico(Double nivelCritico) {
        this.nivelCritico = nivelCritico;
    }
}
