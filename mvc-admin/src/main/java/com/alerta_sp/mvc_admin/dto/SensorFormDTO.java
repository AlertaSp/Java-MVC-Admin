package com.alerta_sp.mvc_admin.dto;

import com.alerta_sp.mvc_admin.model.Sensor;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SensorFormDTO {

    @NotBlank(message = "{sensor.codigo.notblank}")
    private String codigo;

    @NotNull(message = "{sensor.dataInstalacao.notnull}")
    private LocalDate dataInstalacao;

    @NotNull(message = "{sensor.idCorrego.notnull}")
    private Long idCorrego;

    // Status pode vir nulo no formulário de “novo”
    private String status;

    // Construtor vazio (necessário para o Thymeleaf / Spring MVC)
    public SensorFormDTO() { }

    // Construtor auxiliar
    public SensorFormDTO(String codigo, LocalDate dataInstalacao, Long idCorrego, String status) {
        this.codigo = codigo;
        this.dataInstalacao = dataInstalacao;
        this.idCorrego = idCorrego;
        this.status = status;
    }

    // Getters e Setters (obrigatório para que Spring MVC / Thymeleaf faça binding)

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(LocalDate dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public Long getIdCorrego() {
        return idCorrego;
    }

    public void setIdCorrego(Long idCorrego) {
        this.idCorrego = idCorrego;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Constrói um SensorFormDTO a partir de uma entidade Sensor (para edição).
     */
    public static SensorFormDTO fromEntity(Sensor sensor) {
        // Extrai os campos da entidade Sensor
        String codigo = sensor.getCodigo();
        LocalDate dataInstalacao = sensor.getDataInstalacao();
        Long idCorrego = sensor.getCorrego().getId();
        String status = sensor.getStatus();

        return new SensorFormDTO(codigo, dataInstalacao, idCorrego, status);
    }
}
