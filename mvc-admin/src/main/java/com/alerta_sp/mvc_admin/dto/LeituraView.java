package com.alerta_sp.mvc_admin.dto;

import com.alerta_sp.mvc_admin.model.LeituraSensor;

import java.time.format.DateTimeFormatter;

public record LeituraView(Long id, Double nivel, String dataHora, String codigoSensor) {

    public static LeituraView fromEntity(LeituraSensor leitura) {
        String dataHoraFormatada = leitura.getDataHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return new LeituraView(
                leitura.getId(),
                leitura.getNivel(),
                dataHoraFormatada,
                leitura.getSensor().getCodigo()
        );
    }
}
