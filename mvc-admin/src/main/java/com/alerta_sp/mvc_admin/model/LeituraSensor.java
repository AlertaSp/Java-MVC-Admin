package com.alerta_sp.mvc_admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LeituraSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sensor", nullable = false)
    private Sensor sensor;

    @Column(nullable = false)
    private Double nivel;

    private LocalDateTime dataHora = LocalDateTime.now();

    public LeituraSensor() {}

    public LeituraSensor(Sensor sensor, Double nivel) {
        this.sensor = sensor;
        this.nivel = nivel;
    }

    // Getters e setters
    public Long getId() { return id; }
    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }
    public Double getNivel() { return nivel; }
    public void setNivel(Double nivel) { this.nivel = nivel; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}