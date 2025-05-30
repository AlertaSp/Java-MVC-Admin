package com.alerta_sp.mvc_admin.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Corrego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String latitude;
    private String longitude;

    @Column(name = "nivel_alerta")
    private Double nivelAlerta;

    @Column(name = "nivel_critico")
    private Double nivelCritico;

    @OneToMany(mappedBy = "corrego", cascade = CascadeType.ALL)
    private List<Sensor> sensores;

    public Corrego() {}

    // Getters e setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
    public Double getNivelAlerta() { return nivelAlerta; }
    public void setNivelAlerta(Double nivelAlerta) { this.nivelAlerta = nivelAlerta; }
    public Double getNivelCritico() { return nivelCritico; }
    public void setNivelCritico(Double nivelCritico) { this.nivelCritico = nivelCritico; }
    public List<Sensor> getSensores() { return sensores; }
    public void setSensores(List<Sensor> sensores) { this.sensores = sensores; }
}
