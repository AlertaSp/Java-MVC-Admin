package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import com.alerta_sp.mvc_admin.dto.AlertaView;
import com.alerta_sp.mvc_admin.model.Alerta;
import com.alerta_sp.mvc_admin.repository.AlertaRepository;
import com.alerta_sp.mvc_admin.service.AlertaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaServiceImpl implements AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaServiceImpl(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    @Override
    public void salvar(AlertaDTO dto) {
        Alerta alerta = new Alerta();
        alerta.setMensagem(dto.getMensagem());
        alerta.setNivel(dto.getNivel());
        alerta.setCorrego(dto.getCorrego());
        alerta.setDataHora(LocalDateTime.now());

        alertaRepository.save(alerta);
    }

    @Override
    public List<AlertaView> listarUltimosAlertas(int quantidade) {
        return alertaRepository.buscarUltimosAlertas(org.springframework.data.domain.PageRequest.of(0, quantidade))
                .stream()
                .map(alerta -> new AlertaView(alerta.getId(), alerta.getMensagem()))
                .collect(Collectors.toList());
    }

}
