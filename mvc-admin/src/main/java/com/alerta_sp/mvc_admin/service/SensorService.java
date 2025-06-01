package com.alerta_sp.mvc_admin.service;

import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.dto.SensorFormDTO;
import com.alerta_sp.mvc_admin.dto.SensorView;

import java.util.List;
import java.util.Optional;

public interface SensorService {

    SensorView salvarSensor(SensorFormDTO dto);
    List<CorregoView> listarCorregosDisponiveis();
    List<SensorView> listarTodos();
    Optional<SensorView> buscarPorId(Long id);
    void deletarPorId(Long id);
    SensorView atualizarSensor(Long id, SensorFormDTO dto);
}
