package com.alerta_sp.mvc_admin.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SensorFormDTO {
    @NotBlank
    private String codigo;

    private LocalDate dataInstalacao;

    private String status;      

    @NotNull
    private Long idCorrego;

    // getters e setters para cada campo
    public String getCodigo()            { return codigo; }
    public void setCodigo(String codigo){ this.codigo = codigo; }

    public LocalDate getDataInstalacao() { return dataInstalacao; }
    public void setDataInstalacao(LocalDate dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public String getStatus()            { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getIdCorrego()           { return idCorrego; }
    public void setIdCorrego(Long id)    { this.idCorrego = id; }
}

