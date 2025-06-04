package com.alerta_sp.mvc_admin.dto;

import java.io.Serializable;

/**
 * DTO utilizado para transferência de dados de alerta entre sistemas.
 */
public class AlertaMensagemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mensagem;
    private String nivel;
    private String corrego;
    private Long idCorrego;

    // Construtor padrão
    public AlertaMensagemDTO() {}

    // Construtor completo
    public AlertaMensagemDTO(String mensagem, String nivel, String corrego, Long idCorrego) {
        this.mensagem = mensagem;
        this.nivel = nivel;
        this.corrego = corrego;
        this.idCorrego = idCorrego;
    }

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

    @Override
    public String toString() {
        return "AlertaMensagemDTO{" +
                "mensagem='" + mensagem + '\'' +
                ", nivel='" + nivel + '\'' +
                ", corrego='" + corrego + '\'' +
                ", idCorrego=" + idCorrego +
                '}';
    }
}
