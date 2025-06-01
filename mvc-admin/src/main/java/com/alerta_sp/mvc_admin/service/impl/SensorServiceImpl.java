package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.dto.SensorFormDTO;
import com.alerta_sp.mvc_admin.dto.SensorView;
import com.alerta_sp.mvc_admin.model.Corrego;
import com.alerta_sp.mvc_admin.model.Sensor;
import com.alerta_sp.mvc_admin.repository.CorregoRepository;
import com.alerta_sp.mvc_admin.repository.SensorRepository;
import com.alerta_sp.mvc_admin.service.SensorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final CorregoRepository corregoRepository;

    public SensorServiceImpl(SensorRepository sensorRepository, CorregoRepository corregoRepository) {
        this.sensorRepository = sensorRepository;
        this.corregoRepository = corregoRepository;
    }

    @Override
    public SensorView salvarSensor(SensorFormDTO dto) {
        // Busca o córrego referenciado pelo ID
        Corrego corrego = corregoRepository.findById(dto.getIdCorrego())
                .orElseThrow(() -> new IllegalArgumentException("Córrego não encontrado"));

        // Constrói a entidade Sensor
        Sensor sensor = new Sensor(dto.getCodigo(), dto.getDataInstalacao(), corrego);
        sensor.setStatus(dto.getStatus() != null ? dto.getStatus() : "ATIVO");
        Sensor salvo = sensorRepository.save(sensor);

        // Retorna a "visão" do sensor salvo
        return SensorView.fromEntity(salvo);
    }

    @Override
    public List<CorregoView> listarCorregosDisponiveis() {
        return corregoRepository.findAll().stream()
                .map(CorregoView::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SensorView> listarTodos() {
        return sensorRepository.findAll().stream()
                .map(SensorView::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SensorView> buscarPorId(Long id) {
        return sensorRepository.findById(id)
                .map(SensorView::fromEntity);
    }

    @Override
    public void deletarPorId(Long id) {
        sensorRepository.deleteById(id);
    }

    @Override
    public SensorView atualizarSensor(Long id, SensorFormDTO dto) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor não encontrado"));

        sensor.setCodigo(dto.getCodigo());
        sensor.setDataInstalacao(dto.getDataInstalacao());
        sensor.setStatus(dto.getStatus());

        if (!sensor.getCorrego().getId().equals(dto.getIdCorrego())) {
            Corrego corrego = corregoRepository.findById(dto.getIdCorrego())
                    .orElseThrow(() -> new IllegalArgumentException("Córrego não encontrado"));
            sensor.setCorrego(corrego);
        }

        Sensor atualizado = sensorRepository.save(sensor);
        return SensorView.fromEntity(atualizado);
    }
}
