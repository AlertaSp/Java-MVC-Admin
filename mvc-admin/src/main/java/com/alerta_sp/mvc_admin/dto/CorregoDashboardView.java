package com.alerta_sp.mvc_admin.dto;

public record CorregoDashboardView(
        Long id,
        String nome,
        Double nivelAtual,
        String status
) { }
