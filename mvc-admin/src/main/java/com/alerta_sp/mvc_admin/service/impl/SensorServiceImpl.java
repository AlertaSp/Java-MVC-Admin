package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.SensorFormDTO;
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
    public SensorFormDTO salvarSensor(SensorFormDTO dto) {
        Corrego corrego = corregoRepository.findById(dto.idCorrego())
                .orElseThrow(() -> new IllegalArgumentException("Córrego não encontrado"));

        Sensor sensor = new Sensor(dto.codigo(), dto.dataInstalacao(), corrego);
        sensor.setStatus(dto.status() != null ? dto.status() : "ATIVO");
        Sensor salvo = sensorRepository.save(sensor);

        return SensorFormDTO.fromEntity(salvo);
    }

    @Override
    public List<SensorFormDTO> listarTodos() {
        return sensorRepository.findAll().stream()
                .map(SensorFormDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SensorFormDTO> buscarPorId(Long id) {
        return sensorRepository.findById(id)
                .map(SensorFormDTO::fromEntity);
    }

    @Override
    public void deletarPorId(Long id) {
        sensorRepository.deleteById(id);
    }

    @Override
    public SensorFormDTO atualizarSensor(Long id, SensorFormDTO dto) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor não encontrado"));

        sensor.setCodigo(dto.codigo());
        sensor.setDataInstalacao(dto.dataInstalacao());
        sensor.setStatus(dto.status());

        if (!sensor.getCorrego().getId().equals(dto.idCorrego())) {
            Corrego corrego = corregoRepository.findById(dto.idCorrego())
                    .orElseThrow(() -> new IllegalArgumentException("Córrego não encontrado"));
            sensor.setCorrego(corrego);
        }

        Sensor atualizado = sensorRepository.save(sensor);
        return SensorFormDTO.fromEntity(atualizado);
    }
}
