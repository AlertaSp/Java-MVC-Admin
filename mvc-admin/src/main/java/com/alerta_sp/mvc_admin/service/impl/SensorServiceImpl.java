package com.alerta_sp.mvc_admin.service.impl;

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

    public SensorServiceImpl(SensorRepository sensorRepository,
                             CorregoRepository corregoRepository) {
        this.sensorRepository = sensorRepository;
        this.corregoRepository = corregoRepository;
    }

    @Override
    public SensorView salvar(SensorFormDTO dto) {
        // 1) Recupera o córrego
        Corrego corrego = corregoRepository.findById(dto.getIdCorrego())
                .orElseThrow(() -> new IllegalArgumentException("Córrego não encontrado"));

        // 2) Cria nova entidade Sensor
        Sensor sensor = new Sensor();
        sensor.setCodigo(dto.getCodigo());
        sensor.setDataInstalacao(dto.getDataInstalacao());
        // se o campo status vier no DTO, use dto.getStatus(); senão definir um default:
        sensor.setStatus(dto.getStatus() != null ? dto.getStatus() : "ATIVO");
        sensor.setCorrego(corrego);

        Sensor salvo = sensorRepository.save(sensor);
        return SensorView.fromEntity(salvo);
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
    public SensorView atualizar(Long id, SensorFormDTO dto) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor não encontrado"));

        // Se ofertarmos edição de código, dataInstalacao e idCorrego:
        sensor.setCodigo(dto.getCodigo());
        sensor.setDataInstalacao(dto.getDataInstalacao());
        // atualiza status se vier no DTO
        sensor.setStatus(dto.getStatus());

        Corrego corrego = corregoRepository.findById(dto.getIdCorrego())
                .orElseThrow(() -> new IllegalArgumentException("Córrego não encontrado"));
        sensor.setCorrego(corrego);

        Sensor atualizado = sensorRepository.save(sensor);
        return SensorView.fromEntity(atualizado);
    }

    @Override
    public void deletarPorId(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new IllegalArgumentException("Sensor não encontrado.");
        }
        sensorRepository.deleteById(id);
    }
}
