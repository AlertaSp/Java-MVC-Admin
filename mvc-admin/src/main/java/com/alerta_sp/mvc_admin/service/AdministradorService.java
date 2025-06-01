package com.alerta_sp.mvc_admin.service;

import com.alerta_sp.mvc_admin.dto.AdministradorFormDTO;
import com.alerta_sp.mvc_admin.dto.AdministradorView;

import java.util.List;
import java.util.Optional;

public interface AdministradorService {
    AdministradorView salvar(AdministradorFormDTO dto);
    List<AdministradorView> listarTodos();
    Optional<AdministradorView> buscarPorId(Long id);
    AdministradorView atualizar(Long id, AdministradorFormDTO dto);
    void deletarPorId(Long id);
}
