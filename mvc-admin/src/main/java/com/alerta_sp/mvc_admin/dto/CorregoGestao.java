package com.alerta_sp.mvc_admin.dto;

public class CorregoGestao {
    private final Long id;
    private final String nome;
    private final String localizacao;
    private final Double nivelAtual;
    private final Double nivelAlerta;
    private final Double nivelCritico;

    public CorregoGestao(Long id, String nome,
                         String localizacao,
                         Double nivelAtual,
                         Double nivelAlerta,
                         Double nivelCritico) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.nivelAtual = nivelAtual;
        this.nivelAlerta = nivelAlerta;
        this.nivelCritico = nivelCritico;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public Double getNivelAtual() {
        return nivelAtual;
    }

    public Double getNivelAlerta() {
        return nivelAlerta;
    }

    public Double getNivelCritico() {
        return nivelCritico;
    }
}
