package com.alerta_sp.mvc_admin.dto;

import com.alerta_sp.mvc_admin.model.LeituraSensor;

import java.time.format.DateTimeFormatter;

public record LeituraDTO(String dataHora, Double nivel) {
    public static LeituraDTO fromEntity(LeituraSensor leitura) {
        String formatada = leitura.getDataHora()
                .format(DateTimeFormatter.ofPattern("HH:mm"));
        return new LeituraDTO(formatada, leitura.getNivel());
    }
}
