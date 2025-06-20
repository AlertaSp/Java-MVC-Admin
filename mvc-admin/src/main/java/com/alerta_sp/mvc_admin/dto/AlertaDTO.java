package com.alerta_sp.mvc_admin.dto;

import java.io.Serializable;

public class AlertaDTO implements Serializable {

    /** ID de versão para serialização */
    private static final long serialVersionUID = 1L;

    /** Mensagem do alerta */
    private String mensagem;

    /** Nível do alerta: ALERTA ou CRITICO */
    private String nivel;

    /** Nome do córrego (usado para exibição) */
    private String corrego;

    /** ID do córrego (usado para persistência) */
    private Long idCorrego;

    // Getters e Setters

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getCorrego() {
        return corrego;
    }

    public void setCorrego(String corrego) {
        this.corrego = corrego;
    }

    public Long getIdCorrego() {
        return idCorrego;
    }

    public void setIdCorrego(Long idCorrego) {
        this.idCorrego = idCorrego;
    }
}
