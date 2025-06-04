package com.alerta_sp.mvc_admin.dto;

import com.alerta_sp.mvc_admin.model.TipoAlerta;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO para exibição de informações de alerta na camada de visualização.
 */
public class AlertaView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String mensagem;
    private LocalDateTime dataHora;
    private TipoAlerta tipo;
    private String status;
    private boolean resolvido;
    private String corrego;

    // Descrição traduzida do tipo (usada na view)
    private String tipoDescricao;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public TipoAlerta getTipo() {
        return tipo;
    }

    public void setTipo(TipoAlerta tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isResolvido() {
        return resolvido;
    }

    public void setResolvido(boolean resolvido) {
        this.resolvido = resolvido;
    }

    public String getCorrego() {
        return corrego;
    }

    public void setCorrego(String corrego) {
        this.corrego = corrego;
    }

    public String getTipoDescricao() {
        return tipoDescricao;
    }

    public void setTipoDescricao(String tipoDescricao) {
        this.tipoDescricao = tipoDescricao;
    }
}
