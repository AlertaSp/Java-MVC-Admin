package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.LeituraFormDTO;
import com.alerta_sp.mvc_admin.dto.LeituraView;
import com.alerta_sp.mvc_admin.dto.SensorView;
import com.alerta_sp.mvc_admin.model.LeituraSensor;
import com.alerta_sp.mvc_admin.model.Sensor;
import com.alerta_sp.mvc_admin.repository.LeituraSensorRepository;
import com.alerta_sp.mvc_admin.repository.SensorRepository;
import com.alerta_sp.mvc_admin.service.LeituraSensorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeituraSensorServiceImpl implements LeituraSensorService {

    private final LeituraSensorRepository leituraSensorRepository;
    private final SensorRepository sensorRepository;

    public LeituraSensorServiceImpl(LeituraSensorRepository leituraSensorRepository,
                                    SensorRepository sensorRepository) {
        this.leituraSensorRepository = leituraSensorRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public LeituraView salvar(LeituraFormDTO dto) {
        Sensor sensor = sensorRepository.findById(dto.getIdSensor())
                .orElseThrow(() -> new IllegalArgumentException("Sensor não encontrado"));

        // Aqui invertemos a ordem para “(nivel, sensor)”, conforme o construtor definido em LeituraSensor:
        LeituraSensor leitura = new LeituraSensor(dto.getNivel(), sensor);

        LeituraSensor salvo = leituraSensorRepository.save(leitura);
        return LeituraView.fromEntity(salvo);
    }

    @Override
    public List<LeituraView> listarTodas() {
        return leituraSensorRepository.findAll().stream()
                .map(LeituraView::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LeituraView> buscarPorId(Long id) {
        return leituraSensorRepository.findById(id)
                .map(LeituraView::fromEntity);
    }

    @Override
    public void deletarPorId(Long id) {
        leituraSensorRepository.deleteById(id);
    }

    @Override
    public List<SensorView> listarSensoresDisponiveis() {
        return sensorRepository.findAll().stream()
                .map(SensorView::fromEntity)
                .collect(Collectors.toList());
    }
}
