package com.alerta_sp.mvc_admin.service.impl;

import com.alerta_sp.mvc_admin.dto.LeituraFormDTO;
import com.alerta_sp.mvc_admin.dto.LeituraView;
import com.alerta_sp.mvc_admin.model.LeituraSensor;
import com.alerta_sp.mvc_admin.model.Sensor;
import com.alerta_sp.mvc_admin.repository.LeituraSensorRepository;
import com.alerta_sp.mvc_admin.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeituraSensorServiceImplTest {

    @Mock
    private LeituraSensorRepository leituraRepo;
    @Mock
    private SensorRepository sensorRepo;

    @InjectMocks
    private LeituraSensorServiceImpl service;

    @Test
    void salvarDeveLancarExcecaoQuandoSensorNaoExiste() {
        LeituraFormDTO dto = new LeituraFormDTO();
        dto.setIdSensor(1L);
        dto.setNivel(1.2);

        when(sensorRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.salvar(dto));
    }

    @Test
    void salvarDevePersistirQuandoDadosValidos() {
        LeituraFormDTO dto = new LeituraFormDTO();
        dto.setIdSensor(1L);
        dto.setNivel(2.5);

        Sensor sensor = new Sensor();
        ReflectionTestUtils.setField(sensor, "id", 1L);
        when(sensorRepo.findById(1L)).thenReturn(Optional.of(sensor));

        LeituraSensor salvo = new LeituraSensor();
        ReflectionTestUtils.setField(salvo, "id", 10L);
        salvo.setSensor(sensor);
        salvo.setNivel(2.5);
        when(leituraRepo.save(any(LeituraSensor.class))).thenReturn(salvo);

        LeituraView view = service.salvar(dto);
        assertEquals(10L, view.id());
        assertEquals(2.5, view.nivel());
    }
}
