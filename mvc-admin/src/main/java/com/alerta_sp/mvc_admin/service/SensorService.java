package com.alerta_sp.mvc_admin.service;

import com.alerta_sp.mvc_admin.dto.SensorFormDTO;
import com.alerta_sp.mvc_admin.dto.SensorView;

import java.util.List;
import java.util.Optional;

public interface SensorService {

    SensorView salvar(SensorFormDTO dto);
    List<SensorView> listarTodos();
    Optional<SensorView> buscarPorId(Long id);
    SensorView atualizar(Long id, SensorFormDTO dto);
    void deletarPorId(Long id);
}
