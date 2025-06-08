package com.alerta_sp.mvc_admin.dto;

import java.io.Serializable;

/**
 * DTO enviado pelo Admin e consumido pelo projeto User via RabbitMQ.
 */
public class MensagemAlertaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mensagem;
    private String tipo;
    private Long idCorrego;

    public MensagemAlertaDTO() {}

    public MensagemAlertaDTO(String mensagem, String tipo, Long idCorrego) {
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.idCorrego = idCorrego;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdCorrego() {
        return idCorrego;
    }

    public void setIdCorrego(Long idCorrego) {
        this.idCorrego = idCorrego;
    }

    @Override
    public String toString() {
        return "MensagemAlertaDTO{" +
                "mensagem='" + mensagem + '\'' +
                ", tipo='" + tipo + '\'' +
                ", idCorrego=" + idCorrego +
                '}';
    }
}
