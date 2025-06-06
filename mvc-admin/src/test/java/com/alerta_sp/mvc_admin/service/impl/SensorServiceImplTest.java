package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.SensorFormDTO;
import com.alerta_sp.mvc_admin.dto.SensorView;
import com.alerta_sp.mvc_admin.model.Corrego;
import com.alerta_sp.mvc_admin.model.Sensor;
import com.alerta_sp.mvc_admin.repository.CorregoRepository;
import com.alerta_sp.mvc_admin.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceImplTest {

    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private CorregoRepository corregoRepository;

    @InjectMocks
    private SensorServiceImpl service;

    @Test
    void salvarDeveLancarExcecaoQuandoCorregoNaoExiste() {
        SensorFormDTO dto = new SensorFormDTO();
        dto.setCodigo("S1");
        dto.setIdCorrego(1L);

        when(corregoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.salvar(dto));
    }

    @Test
    void salvarDevePersistirQuandoDadosValidos() {
        SensorFormDTO dto = new SensorFormDTO();
        dto.setCodigo("S1");
        dto.setIdCorrego(1L);

        Corrego corrego = new Corrego();
        corrego.setId(1L);
        when(corregoRepository.findById(1L)).thenReturn(Optional.of(corrego));

        Sensor saved = new Sensor();
        saved.setId(10L);
        saved.setCodigo("S1");
        saved.setCorrego(corrego);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(saved);

        SensorView view = service.salvar(dto);
        assertEquals(10L, view.id());
        assertEquals("S1", view.codigo());
    }
}
