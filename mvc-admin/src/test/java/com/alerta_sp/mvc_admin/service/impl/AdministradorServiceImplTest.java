package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.AdministradorFormDTO;
import com.alerta_sp.mvc_admin.dto.AdministradorView;
import com.alerta_sp.mvc_admin.model.Administrador;
import com.alerta_sp.mvc_admin.repository.AdministradorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdministradorServiceImplTest {

    @Mock
    private AdministradorRepository administradorRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AdministradorServiceImpl service;

    @Test
    void salvarDeveLancarExcecaoQuandoUsernameJaExiste() {
        AdministradorFormDTO dto = new AdministradorFormDTO();
        dto.setUsername("admin");
        dto.setSenha("123");

        when(administradorRepository.existsByUsername("admin")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.salvar(dto));
        verify(administradorRepository, never()).save(any());
    }

    @Test
    void salvarDevePersistirQuandoUsernameNaoExiste() {
        AdministradorFormDTO dto = new AdministradorFormDTO();
        dto.setUsername("novo");
        dto.setSenha("abc");

        when(administradorRepository.existsByUsername("novo")).thenReturn(false);
        when(passwordEncoder.encode("abc")).thenReturn("enc");

        Administrador saved = new Administrador();
        saved.setId(1L);
        saved.setUsername("novo");
        saved.setSenha("enc");
        when(administradorRepository.save(any(Administrador.class))).thenReturn(saved);

        AdministradorView view = service.salvar(dto);
        assertEquals(1L, view.id());
        assertEquals("novo", view.username());
    }
}
