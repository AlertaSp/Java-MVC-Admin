package com.alerta_sp.mvc_admin.service;

import com.alerta_sp.mvc_admin.dto.AlertaView;
import java.util.List;

public interface AlertaService {
    List<AlertaView> listarUltimosAlertas(int quantidade);
}
