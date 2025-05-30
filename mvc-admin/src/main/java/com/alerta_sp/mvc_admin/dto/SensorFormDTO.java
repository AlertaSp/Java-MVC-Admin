package com.alerta_sp.mvc_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SensorFormDTO {
    @NotBlank
    private String codigo;

    private String dataInstalacao;

    @NotNull
    private Long idCorrego;

    // Getters e Setters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(String dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public Long getIdCorrego() {
        return idCorrego;
    }

    public void setIdCorrego(Long idCorrego) {
        this.idCorrego = idCorrego;
    }
}
