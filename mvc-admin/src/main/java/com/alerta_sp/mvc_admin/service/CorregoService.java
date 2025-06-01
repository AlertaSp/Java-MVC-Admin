package com.alerta_sp.mvc_admin.service;

import com.alerta_sp.mvc_admin.dto.CorregoFormDTO;
import com.alerta_sp.mvc_admin.dto.CorregoView;

import java.util.List;
import java.util.Optional;

public interface CorregoService {

    CorregoView salvar(CorregoFormDTO dto);
    List<CorregoView> listarTodos();
    Optional<CorregoView> buscarPorId(Long id);
    CorregoView atualizar(Long id, CorregoFormDTO dto);
    void deletarPorId(Long id);
}
