package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.CorregoDashboardView;
import com.alerta_sp.mvc_admin.dto.CorregoFormDTO;
import com.alerta_sp.mvc_admin.dto.CorregoView;
import com.alerta_sp.mvc_admin.model.Corrego;
import com.alerta_sp.mvc_admin.repository.CorregoRepository;
import com.alerta_sp.mvc_admin.service.CorregoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CorregoServiceImpl implements CorregoService {

    private final CorregoRepository corregoRepository;
    // Se você tiver o serviço de leituras, injete aqui:
    // private final LeituraSensorService leituraSensorService;

    public CorregoServiceImpl(CorregoRepository corregoRepository /*,
                              LeituraSensorService leituraSensorService */) {
        this.corregoRepository = corregoRepository;
        // this.leituraSensorService = leituraSensorService;
    }

    @Override
    public CorregoView salvar(CorregoFormDTO dto) {
        if (corregoRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Já existe um córrego com este nome.");
        }
        Corrego corrego = new Corrego();
        corrego.setNome(dto.getNome());
        corrego.setLatitude(dto.getLatitude());
        corrego.setLongitude(dto.getLongitude());
        corrego.setNivelAlerta(dto.getNivelAlerta());
        corrego.setNivelCritico(dto.getNivelCritico());
        Corrego salvo = corregoRepository.save(corrego);
        return CorregoView.fromEntity(salvo);
    }

    @Override
    public List<CorregoView> listarTodos() {
        return corregoRepository.findAll().stream()
                .map(CorregoView::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CorregoView> buscarPorId(Long id) {
        return corregoRepository.findById(id)
                .map(CorregoView::fromEntity);
    }

    @Override
    public CorregoView atualizar(Long id, CorregoFormDTO dto) {
        Corrego corrego = corregoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Córrego não encontrado."));
        corrego.setNome(dto.getNome());
        corrego.setLatitude(dto.getLatitude());
        corrego.setLongitude(dto.getLongitude());
        corrego.setNivelAlerta(dto.getNivelAlerta());
        corrego.setNivelCritico(dto.getNivelCritico());
        Corrego atualizado = corregoRepository.save(corrego);
        return CorregoView.fromEntity(atualizado);
    }

    @Override
    public void deletarPorId(Long id) {
        if (!corregoRepository.existsById(id)) {
            throw new IllegalArgumentException("Córrego não encontrado.");
        }
        corregoRepository.deleteById(id);
    }

    @Override
    public List<CorregoDashboardView> listarTodosComStatus() {
        return corregoRepository.findAll().stream()
                .map(corrego -> {
                    // ★☆☆★ Substitua o stub abaixo pela lógica real de capturar a última leitura:
                    Double nivelAtual = 0.0;
                    // Exemplo alternativo de stub:
                    // Double nivelAtual = corrego.getNivelAlerta();

                    String status;
                    if (nivelAtual >= corrego.getNivelCritico()) {
                        status = "vermelho";
                    } else if (nivelAtual >= corrego.getNivelAlerta()) {
                        status = "amarelo";
                    } else {
                        status = "verde";
                    }

                    return new CorregoDashboardView(
                            corrego.getId(),
                            corrego.getNome(),
                            nivelAtual,
                            status
                    );
                })
                .collect(Collectors.toList());
    }
}
