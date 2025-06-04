package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import com.alerta_sp.mvc_admin.dto.AlertaView;
import com.alerta_sp.mvc_admin.model.Alerta;
import com.alerta_sp.mvc_admin.model.Corrego;
import com.alerta_sp.mvc_admin.model.TipoAlerta;
import com.alerta_sp.mvc_admin.repository.AlertaRepository;
import com.alerta_sp.mvc_admin.repository.CorregoRepository;
import com.alerta_sp.mvc_admin.service.AlertaService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaServiceImpl implements AlertaService {

    private final AlertaRepository alertaRepository;
    private final CorregoRepository corregoRepository;

    public AlertaServiceImpl(AlertaRepository alertaRepository, CorregoRepository corregoRepository) {
        this.alertaRepository = alertaRepository;
        this.corregoRepository = corregoRepository;
    }

    private Alerta toEntity(AlertaDTO dto) {
        Alerta alerta = new Alerta();
        alerta.setMensagem(dto.getMensagem());

        Corrego corrego = corregoRepository.findById(dto.getIdCorrego())
                .orElseThrow(() -> new IllegalArgumentException("Córrego não encontrado: id=" + dto.getIdCorrego()));

        alerta.setCorrego(corrego);
        alerta.setTipo(TipoAlerta.valueOf(dto.getNivel()));
        alerta.setStatus("ATIVO");
        alerta.setResolvido(false);
        alerta.setDataHora(LocalDateTime.now());

        return alerta;
    }

    private AlertaView toView(Alerta alerta) {
        AlertaView view = new AlertaView();
        view.setId(alerta.getId());
        view.setMensagem(alerta.getMensagem());
        view.setDataHora(alerta.getDataHora());
        view.setTipo(alerta.getTipo());
        view.setStatus(alerta.getStatus());
        view.setResolvido(alerta.isResolvido());
        view.setCorrego(alerta.getCorrego().getNome());
        return view;
    }

    @Override
    public List<AlertaView> listarUltimosAlertas(int limite) {
        return alertaRepository.buscarUltimosAlertas(Pageable.ofSize(limite))
                .stream()
                .map(this::toView)
                .collect(Collectors.toList());
    }

    @Override
    public void salvar(AlertaDTO dto) {
        alertaRepository.save(toEntity(dto));
    }
}
