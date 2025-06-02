package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.AdministradorFormDTO;
import com.alerta_sp.mvc_admin.dto.AdministradorView;
import com.alerta_sp.mvc_admin.model.Administrador;
import com.alerta_sp.mvc_admin.repository.AdministradorRepository;
import com.alerta_sp.mvc_admin.service.AdministradorService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdministradorServiceImpl(AdministradorRepository administradorRepository,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdministradorView salvar(AdministradorFormDTO dto) {
        // Verifica se já existe administrador com mesmo username
        if (administradorRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username já em uso.");
        }

        // Cria a entidade Administrador e faz hash da senha
        Administrador admin = new Administrador();
        admin.setUsername(dto.getUsername());
        admin.setSenha(passwordEncoder.encode(dto.getSenha()));

        Administrador salvo = administradorRepository.save(admin);
        return AdministradorView.fromEntity(salvo);
    }

    @Override
    public List<AdministradorView> listarTodos() {
        return administradorRepository.findAll().stream()
                .map(AdministradorView::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AdministradorView> buscarPorId(Long id) {
        return administradorRepository.findById(id)
                .map(AdministradorView::fromEntity);
    }

    @Override
    public AdministradorView atualizar(Long id, AdministradorFormDTO dto) {
        Administrador admin = administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado."));

        // Atualiza username e, se fornecida, a senha
        if (!admin.getUsername().equals(dto.getUsername())) {
            if (administradorRepository.existsByUsername(dto.getUsername())) {
                throw new IllegalArgumentException("Username já em uso.");
            }
            admin.setUsername(dto.getUsername());
        }

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            admin.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Administrador atualizado = administradorRepository.save(admin);
        return AdministradorView.fromEntity(atualizado);
    }

    @Override
    public void deletarPorId(Long id) {
        if (!administradorRepository.existsById(id)) {
            throw new IllegalArgumentException("Administrador não encontrado.");
        }
        administradorRepository.deleteById(id);
    }
}
