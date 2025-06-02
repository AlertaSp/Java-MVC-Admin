package com.alerta_sp.mvc_admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leitura_sensor")
public class LeituraSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leitura")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sensor", nullable = false)
    private Sensor sensor;

    @Column(name = "nivel")
    private Double nivel;

    @Column(name = "data_hora")
    private LocalDateTime dataHora = LocalDateTime.now();

    public LeituraSensor() {}

    public LeituraSensor(Double nivel, Sensor sensor) {
        this.nivel = nivel;
        this.sensor = sensor;
        this.dataHora = LocalDateTime.now();
    }

    // getters e setters

    public Long getId() { return id; }
    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }
    public Double getNivel() { return nivel; }
    public void setNivel(Double nivel) { this.nivel = nivel; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
