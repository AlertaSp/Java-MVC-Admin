package com.alerta_sp.mvc_admin.service;

import com.alerta_sp.mvc_admin.dto.SensorFormDTO;

import java.util.List;
import java.util.Optional;

public interface SensorService {
    SensorFormDTO salvarSensor(SensorFormDTO dto);
    List<SensorFormDTO> listarTodos();
    Optional<SensorFormDTO> buscarPorId(Long id);
    void deletarPorId(Long id);
    SensorFormDTO atualizarSensor(Long id, SensorFormDTO dto);
}
