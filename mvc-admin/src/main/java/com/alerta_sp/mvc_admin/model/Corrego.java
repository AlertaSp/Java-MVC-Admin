package com.alerta_sp.mvc_admin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "corrego")
public class Corrego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corrego")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "nivel_alerta")
    private Double nivelAlerta;

    @Column(name = "nivel_critico")
    private Double nivelCritico;

    // getters e setters omitidos
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
}
