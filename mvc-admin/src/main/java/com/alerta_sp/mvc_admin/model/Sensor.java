package com.alerta_sp.mvc_admin.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Column(name = "status")
    private String status = "ATIVO";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_corrego", nullable = false)
    private Corrego corrego;

    public Sensor() {}

    public Sensor(String codigo, LocalDate dataInstalacao, Corrego corrego) {
        this.codigo = codigo;
        this.dataInstalacao = dataInstalacao;
        this.corrego = corrego;
    }

    // getters e setters

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDate getDataInstalacao() { return dataInstalacao; }
    public void setDataInstalacao(LocalDate dataInstalacao) { this.dataInstalacao = dataInstalacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Corrego getCorrego() { return corrego; }
    public void setCorrego(Corrego corrego) { this.corrego = corrego; }
}
