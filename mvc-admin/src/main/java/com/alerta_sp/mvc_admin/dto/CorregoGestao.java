package com.alerta_sp.mvc_admin.dto;

public class CorregoGestao {
    private Long id;
    private String nome;
    private String localizacao;
    private Double nivelAtual;
    private Double nivelAlerta;
    private Double nivelCritico;
    private String status; // <-- ADICIONE ISSO

    public CorregoGestao(Long id, String nome, String localizacao,
                         Double nivelAtual, Double nivelAlerta, Double nivelCritico) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.nivelAtual = nivelAtual;
        this.nivelAlerta = nivelAlerta;
        this.nivelCritico = nivelCritico;

        // Calcular status diretamente aqui
        this.status = calcularStatus(nivelAtual, nivelAlerta, nivelCritico);
    }

    private String calcularStatus(Double atual, Double alerta, Double critico) {
        if (atual >= critico) return "vermelho";
        if (atual >= alerta) return "amarelo";
        return "verde";
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getLocalizacao() { return localizacao; }
    public Double getNivelAtual() { return nivelAtual; }
    public Double getNivelAlerta() { return nivelAlerta; }
    public Double getNivelCritico() { return nivelCritico; }
    public String getStatus() { return status; }
}
