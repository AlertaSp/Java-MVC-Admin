package com.alerta_sp.mvc_admin.service;

import com.alerta_sp.mvc_admin.dto.LeituraFormDTO;
import com.alerta_sp.mvc_admin.dto.LeituraView;
import com.alerta_sp.mvc_admin.dto.SensorView;  // vamos reutilizar SensorView para dropdown
import java.util.List;
import java.util.Optional;

public interface LeituraSensorService {

    LeituraView salvar(LeituraFormDTO dto);
    List<LeituraView> listarTodas();
    Optional<LeituraView> buscarPorId(Long id);
    void deletarPorId(Long id);
    List<SensorView> listarSensoresDisponiveis();

}
