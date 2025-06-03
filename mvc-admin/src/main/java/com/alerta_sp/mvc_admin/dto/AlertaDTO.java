package com.alerta_sp.mvc_admin.dto;

import java.io.Serializable;

public class AlertaDTO implements Serializable {
    private String mensagem;
    private String nivel;
    private String corrego;

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
}
