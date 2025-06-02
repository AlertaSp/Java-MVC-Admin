package com.alerta_sp.mvc_admin.dto;

public record AlertaView(
        Long idAlerta,
        String mensagem
) {
    public AlertaView(String mensagem) {
        this(null, mensagem);
    }
}
