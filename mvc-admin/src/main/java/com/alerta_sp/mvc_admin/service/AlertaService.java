package com.alerta_sp.mvc_admin.service;

import com.alerta_sp.mvc_admin.dto.AlertaDTO;
import com.alerta_sp.mvc_admin.dto.AlertaView;

import java.util.List;

public interface AlertaService {
    void salvar(AlertaDTO dto);
    List<AlertaView> listarUltimosAlertas(int quantidade);
}
