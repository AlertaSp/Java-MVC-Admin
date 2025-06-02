package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.AlertaView;
import com.alerta_sp.mvc_admin.service.AlertaService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlertaServiceImpl implements AlertaService {
    @Override
    public List<AlertaView> listarUltimosAlertas(int quantidade) {
        return List.of();
    }
}
